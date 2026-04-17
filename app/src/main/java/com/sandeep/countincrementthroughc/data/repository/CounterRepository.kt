package com.sandeep.countincrementthroughc.data.repository

import android.util.Log
import com.sandeep.countincrementthroughc.nativeLib.NativeBridge
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Repository layer responsible for interacting with native bridge.
 *
 * Acts as a single source of truth for counter updates and
 * abstracts JNI communication from ViewModel.
 */
class CounterRepository {
    private val _counterFlow = MutableSharedFlow<Int>(replay = 1)
    val counterFlow = _counterFlow.asSharedFlow()

    init {
        /**
         * Register callback to receive data from native layer.
         */
        NativeBridge.setCallback { count ->
            Log.d("sandeep", "Flow emit: $count")
            _counterFlow.tryEmit(count)
        }
    }

    /**
     * Triggers increment operation in native layer.
     */
    fun increment() {
        NativeBridge.incrementCount()
    }

    /**
     * Cleans up native resources.
     */
    fun cleanup() {
        NativeBridge.cleanup()
    }
}