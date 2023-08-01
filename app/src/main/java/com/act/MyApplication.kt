package com.act

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Log.e("11111","onStart")
            }

            override fun onResume(owner: LifecycleOwner) { // 应用前台
                super.onResume(owner)
            }

            override fun onPause(owner: LifecycleOwner) { // 应用后台
                super.onPause(owner)
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                Log.e("11111","onStop")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
            }
        })
    }
}