package com.sandeep.countincrementthroughc.nativeLib

import android.util.Log

/**
 * Bridge between Kotlin and native (C++) layer using JNI.
 *
 * Responsibilities:
 * - Loads native library
 * - Exposes native functions to Kotlin
 * - Receives callbacks from native layer
 */
object NativeBridge {

    init {
        try {
            System.loadLibrary("native-increment-lib")
        } catch (e: UnsatisfiedLinkError) {
            e.printStackTrace()
        }
    }

    /**
     * Callback invoked when native layer returns a result.
     */
    private var callback: ((Int) -> Unit)? = null

    /**
     * Sets callback to receive counter updates from native layer.
     */
    fun setCallback(cb: (Int) -> Unit) {
        callback = cb
    }

    /**
     * Calls native function to increment counter asynchronously.
     */
    external fun incrementCount()

    /**
     * Cleans up native resources (global references).
     */
    external fun cleanup()

    /**
     * Called from native (C++) layer via JNI.
     *
     * @param count Updated counter value
     */
    @JvmStatic
    fun onNativeResult(count: Int) {
        Log.d("sandeep", "Native result: $count")
        callback?.invoke(count)
    }
}