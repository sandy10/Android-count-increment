package com.sandeep.countincrementthroughc.data.model

/**
 * Data model representing a single counter update coming from native layer.
 *
 * @property count The current counter value returned from C++ layer
 * @property timestamp The time at which this value was received in UI layer
 */
data class Counter(
    val count: Int = 0,
    val timestamp: String = ""
)
