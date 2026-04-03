
#include <jni.h>
#include <thread>
#include <chrono>

JavaVM* gJvm = nullptr;
jclass gClass = nullptr;
jmethodID gMethod = nullptr;

int counter = 0;

jint JNI_OnLoad(JavaVM* vm, void*) {
    gJvm = vm;
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_sandeep_countincrementthroughc_nativeLib_NativeBridge_incrementCount(
        JNIEnv *env,
        jobject thiz) {

    // Cache class + method (SAFE here)
    if (gClass == nullptr) {
        jclass localClass = env->FindClass("com/sandeep/countincrementthroughc/nativeLib/NativeBridge");
        gClass = (jclass) env->NewGlobalRef(localClass);

        gMethod = env->GetStaticMethodID(
                gClass,
                "onNativeResult",
                "(I)V"
        );
    }

    std::thread([]() {

        int value = ++counter;

        std::this_thread::sleep_for(std::chrono::seconds(2));

        JNIEnv* env;
        gJvm->AttachCurrentThread(&env, nullptr);
        env->CallStaticVoidMethod(gClass, gMethod, value);
        gJvm->DetachCurrentThread();

    }).detach();
}