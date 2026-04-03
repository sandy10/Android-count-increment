# Android-count-increment

This application demonstrates integration of a native C++ library 
using Android NDK with a Jetpack Compose UI following the MVVM architecture.
When the user clicks a button, a function in the C++ layer is invoked via JNI,
which increments a counter asynchronously using a background thread. 
After a short delay, the updated count is sent back to the Kotlin layer 
through a JNI callback. The ViewModel receives this data and updates the UI 
using StateFlow, displaying the count in a list.