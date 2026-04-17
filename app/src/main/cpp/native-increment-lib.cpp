
#include <jni.h>
#include <thread>
#include <chrono>
#include <mutex>
#include <atomic>
#include <queue>
#include <condition_variable>
#include <android/log.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "NativeLib", __VA_ARGS__)

JavaVM* gJvm = nullptr;
jclass gClass = nullptr;
jmethodID gMethod = nullptr;

std::atomic<int> counter(0);
std::mutex initMutex;

std::queue<int> taskQueue;
std::mutex queueMutex;
std::condition_variable cv;


void worker() {
    while (true) {
        std::unique_lock<std::mutex> lock(queueMutex);
        cv.wait(lock, [] { return !taskQueue.empty(); });

        int value = taskQueue.front();
        taskQueue.pop();
        lock.unlock();

        std::this_thread::sleep_for(std::chrono::seconds(2));

        JNIEnv* env;
        gJvm->AttachCurrentThread(&env, nullptr);

        if (gClass && gMethod) {
            env->CallStaticVoidMethod(gClass, gMethod, value);
        }

        gJvm->DetachCurrentThread();
    }
}

jint JNI_OnLoad(JavaVM* vm, void*) {
    gJvm = vm;
    std::thread(worker).detach(); // single worker
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_sandeep_countincrementthroughc_nativeLib_NativeBridge_incrementCount(
        JNIEnv *env,
        jobject thiz) {

    // Cache class + method (SAFE here)
if (gClass == nullptr) {
    std::lock_guard<std::mutex> lock(initMutex);
    if (gClass == nullptr) {
        jclass localClass = env->FindClass("com/sandeep/countincrementthroughc/nativeLib/NativeBridge");
        gClass = (jclass) env->NewGlobalRef(localClass);
        env->DeleteLocalRef(localClass);

        gMethod = env->GetStaticMethodID(
                gClass,
                "onNativeResult",
                "(I)V"
        );
        }
    }
    int value = ++counter;
    {
        std::lock_guard<std::mutex> lock(queueMutex);
        taskQueue.push(value);
    }
    cv.notify_one();

    LOGD("incrementCount triggered");
    LOGD("value pushed: %d", value);
}



extern "C"
JNIEXPORT void JNICALL
Java_com_sandeep_countincrementthroughc_nativeLib_NativeBridge_cleanup(
        JNIEnv *env,
        jobject thiz) {

    if (gClass != nullptr) {
        env->DeleteGlobalRef(gClass);
        gClass = nullptr;
    }
}