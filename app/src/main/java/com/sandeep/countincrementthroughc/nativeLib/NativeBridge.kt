package com.sandeep.countincrementthroughc.nativeLib

object NativeBridge {

    init {
        System.loadLibrary("native-increment-lib")
    }

    external fun incrementCount()

    var onResult: ((Int) -> Unit)? = null

    @JvmStatic
    fun onNativeResult(count: Int) {
        onResult?.invoke(count)
    }
}