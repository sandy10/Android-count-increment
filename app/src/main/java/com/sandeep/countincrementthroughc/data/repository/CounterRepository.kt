package com.sandeep.countincrementthroughc.data.repository

import android.util.Log
import com.sandeep.countincrementthroughc.nativeLib.NativeBridge
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CounterRepository {
    private val _counterFlow = MutableSharedFlow<Int>(replay = 1)
    val counterFlow = _counterFlow.asSharedFlow()

    init {
        NativeBridge.setCallback { count ->
            Log.d("sandeep", "Flow emit: $count")
            _counterFlow.tryEmit(count)
        }
    }

    fun increment() {
        NativeBridge.incrementCount()
    }

    fun cleanup() {
        NativeBridge.cleanup()
    }
}