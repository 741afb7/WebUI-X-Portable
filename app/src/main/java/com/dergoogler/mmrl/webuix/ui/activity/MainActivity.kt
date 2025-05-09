package com.dergoogler.mmrl.webuix.ui.activity

import android.os.Bundle
import androidx.compose.animation.Crossfade
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.dergoogler.mmrl.datastore.model.WorkingMode
import com.dergoogler.mmrl.datastore.model.WorkingMode.Companion.isRoot
import com.dergoogler.mmrl.datastore.model.WorkingMode.Companion.isSetup
import com.dergoogler.mmrl.platform.Platform
import com.dergoogler.mmrl.webuix.ui.activity.webui.WebUIActivity
import com.dergoogler.mmrl.webuix.ui.screens.MainScreen
import com.dergoogler.mmrl.webuix.util.BaseActivity
import com.dergoogler.mmrl.webuix.util.setBaseContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var isLoading by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { isLoading }

        setBaseContent {
            val userPreferences by userPreferencesRepository.data
                .collectAsStateWithLifecycle(initialValue = null)

            val preferences = if (userPreferences == null) {
                return@setBaseContent
            } else {
                isLoading = false
                checkNotNull(userPreferences)
            }


            LaunchedEffect(userPreferences) {

                Platform.init {
                    context = baseContext
                    platform = Platform.KsuNext
                }

                setActivityEnabled<WebUIActivity>(preferences.workingMode.isRoot)
            }

            Crossfade(
                targetState = preferences.workingMode.isSetup,
                label = "MainActivity"
            ) { isSetup ->
                if (isSetup) {
                    SetupScreen(::setWorkingMode)
                } else {
                    MainScreen()
                }
            }
        }
    }

    private fun setWorkingMode(value: WorkingMode) {
        lifecycleScope.launch {
            userPreferencesRepository.setWorkingMode(value)
        }
    }
}
