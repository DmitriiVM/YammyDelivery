package com.dvm.dish.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.db.api.models.Review
import com.dvm.dish.R
import com.dvm.utils.extensions.formatAsDate

@Composable
fun ReviewHeader(
    rating: Double,
    color: Color
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
            onClick = { },
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