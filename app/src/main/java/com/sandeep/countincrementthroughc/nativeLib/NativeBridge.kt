package com.sandeep.countincrementthroughc.nativeLib

import android.util.Log

object NativeBridge {

    init {
        try {
            System.loadLibrary("native-increment-lib")
        } catch (e: UnsatisfiedLinkError) {
            e.printStackTrace()
        }
    }

    private var callback: ((Int) -> Unit)? = null

    fun setCallback(cb: (Int) -> Unit) {
        callback = cb
    }

    external fun incrementCount()
    external fun cleanup()

    @JvmStatic
    fun onNativeResult(count: Int) {
        Log.d("sandeep", "Native result: $count")
        callback?.invoke(count)
    }
}