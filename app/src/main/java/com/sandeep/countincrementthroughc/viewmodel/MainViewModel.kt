package com.sandeep.countincrementthroughc.viewmodel

import androidx.lifecycle.ViewModel
import com.sandeep.countincrementthroughc.model.Counter
import com.sandeep.countincrementthroughc.nativeLib.NativeBridge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _data = MutableStateFlow<List<Counter>>(emptyList())
    val items = _data.asStateFlow()

    init {
        NativeBridge.onResult = { count ->
            val timeStamp = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())
            val newItem = Counter(count, timeStamp)

            _data.value += newItem
        }
    }

    fun onButtonClick() {
        NativeBridge.incrementCount()
    }
}