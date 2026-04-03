package com.sandeep.countincrementthroughc.viewmodel

import androidx.lifecycle.ViewModel
import com.sandeep.countincrementthroughc.model.Counter
import com.sandeep.countincrementthroughc.nativeLib.NativeBridge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class MainViewModel : ViewModel() {

    private val _data = MutableStateFlow<List<Counter>>(emptyList())
    val items = _data.asStateFlow()

    init {
        NativeBridge.onResult = { count ->
            val newItem = Counter(count)

            _data.value += newItem
        }
    }

    fun onButtonClick() {
        NativeBridge.incrementCount()
    }
}