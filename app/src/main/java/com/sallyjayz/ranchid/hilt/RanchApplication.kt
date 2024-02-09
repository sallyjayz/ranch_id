package com.sallyjayz.ranchid.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.intercom.android.sdk.Intercom

@HiltAndroidApp
class RanchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Intercom.initialize(this, "android_sdk-de018329b81add8fa2d31d8f346e7101c8c7fcab", "h49dqmld")
    }
}