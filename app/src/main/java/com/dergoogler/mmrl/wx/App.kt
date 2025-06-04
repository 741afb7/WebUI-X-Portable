package com.dergoogler.mmrl.wx

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.dergoogler.mmrl.platform.PlatformManager
import com.dergoogler.mmrl.wx.app.utils.NotificationUtils
import com.dergoogler.mmrl.wx.service.PlatformService
import com.dergoogler.mmrl.wx.util.extractZipFromAssets
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // KSU WebUI Demo by KOWX712 @ GitHub
            val output = File(getExternalFilesDir(null), "modules/ksuwebui_demo")
            extractZipFromAssets("webuix-demo.zip", output)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "onCreate: $e")
        }

        NotificationUtils.init(this)

        PlatformManager.setHiddenApiExemptions()
    }

    companion object {
        const val TAG = "App"
    }
}