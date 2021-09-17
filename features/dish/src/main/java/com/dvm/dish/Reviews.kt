package com.dvm.dish

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dvm.database.Review
import com.dvm.ui.components.ProgressButton
import com.dvm.utils.extensions.formatAsDate

@Composable
fun ReviewHeader(
    rating: Double,
    color: Color,
    onAddReviewClick: () -> Unit
) {
    Icon(
        painter = painterResource(R.drawable.icon_review),
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 20.dp),
    )

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(R.string.dish_text_reviews),
            modifier = Modifier.padding(end = 10.dp),
            style = MaterialTheme.typography.h6
        )
        Text(
            text = "★ ${String.format("%.1f", rating)}/5",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = color
        )

        OutlinedButton(
            onClick = onAddReviewClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = color
            )
        ) {
            Text(stringResource(R.string.dish_button_add_review))
        }
    }
}

@Composable
fun ReviewItem(
    review: Review,
    color: Color
) {
    Surface(
        elevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${review.author}, ${review.date.formatAsDate()}",
                    color = color,
                    modifier = Modifier.weight(1f)
                )
                var stars = ""
                repeat(review.rating) { stars = stars.plus("★") }
                Text(
                    text = stars,
                    color = color
                )
            }
            Text(
                text = review.text,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ReviewDialog(
    onDismiss: () -> Unit,
    onAddReview: (Int, String) -> Unit,
    networkCall: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = if (networkCall) {
            DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        } else {
            DialogProperties()
        }
    ) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(Modifier.padding(15.dp)) {

                var rating by rememberSaveable { mutableStateOf(0) }
                var text by rememberSaveable { mutableStateOf("") }

                Text(
                    text = stringResource(R.string.dish_review_title),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(5) { index ->
                        IconButton(onClick = { rating = index + 1 }) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (rating >= index + 1) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    enabled = !networkCall,
                    value = text,
                    onValueChange = { text = it }
                )

                Spacer(Modifier.height(30.dp))
                ProgressButton(
                    text = stringResource(R.string.dish_review_send),
                    progress = networkCall,
                    enabled = rating > 0 && !networkCall,
                    onClick = {
                        keyboardController?.hide()
                        onAddReview(rating, text)
                    }
                )
            }
        }
    }
}