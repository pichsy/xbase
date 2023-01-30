package com.pichs.base.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class SimpleLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner) {
    }

}