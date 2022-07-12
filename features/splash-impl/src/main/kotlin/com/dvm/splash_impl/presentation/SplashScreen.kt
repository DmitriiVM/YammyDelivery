package com.dvm.splash_impl.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dvm.splash_impl.R
import org.koin.androidx.compose.getViewModel

@Composable
@Suppress("UnusedPrivateMember")
internal fun SplashScreen(
    // to initialize ViewModel
    viewModel: SplashViewModel = getViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val configuration = LocalConfiguration.current
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> Modifier.fillMaxHeight()
                else -> Modifier.fillMaxWidth()
            }
        )
    }
}