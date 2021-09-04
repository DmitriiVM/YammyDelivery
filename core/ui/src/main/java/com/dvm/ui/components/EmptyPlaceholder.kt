package com.dvm.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun EmptyPlaceholder(
    text: String,
    @androidx.annotation.RawRes resId: Int,
    modifier: Modifier = Modifier,
    repeatCount: Int = Integer.MAX_VALUE
) {
    Box(
        modifier = modifier
    ) {
        Column(Modifier.fillMaxWidth()) {
            val configuration = LocalConfiguration.current
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> Modifier.fillMaxHeight()
                        else -> Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp)
                            .padding(vertical = 20.dp)
                    }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.TopCenter),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            }
        }
    }
}