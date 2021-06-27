package com.dvm.splash

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState

@Composable
internal fun Splash() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val configuration = LocalConfiguration.current

        val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.splash) }
        val animationState =
            rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
        LottieAnimation(
            spec = animationSpec,
            animationState = animationState,
            modifier = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> Modifier.fillMaxHeight()
                else -> Modifier.fillMaxWidth()
            }
        )
    }
}