package com.sandeep.countincrementthroughc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.countincrementthroughc.data.model.Counter
import com.sandeep.countincrementthroughc.data.repository.CounterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(
    private val repository: CounterRepository = CounterRepository()
) : ViewModel() {

    private val _data = MutableStateFlow<List<Counter>>(emptyList())
    val items = _data.asStateFlow()

    init {
        observeCounter()
    }

    private fun observeCounter() {
        viewModelScope.launch {
            repository.counterFlow.collect { count ->

                val timeStamp = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())

                val newItem = Counter(count, timeStamp)
                _data.value += newItem
            }
        }
    }

    fun onButtonClick() {
        viewModelScope.launch {
            withTimeoutOrNull(3000) {
                repository.increment()
            } ?: Log.e("VM", "Timeout from native")
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.cleanup()
    }
}