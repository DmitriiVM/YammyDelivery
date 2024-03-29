package com.dvm.menu_impl.presentation.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.ui.components.Image
import com.dvm.ui.R as CoreR

@Composable
internal fun DishItem(
    dish: CardDish,
    modifier: Modifier = Modifier,
    onDishClick: (dishId: String) -> Unit,
    onAddToCartClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onDishClick(dish.id) }
    ) {
        ConstraintLayout(Modifier.padding(8.dp)) {

            val (image, description, addButton, favoriteButton) = createRefs()

            Image(
                data = dish.image,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .constrainAs(image) {}
            )

            IconButton(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .constrainAs(addButton) {
                        end.linkTo(image.end)
                        centerAround(image.bottom)
                    },
                onClick = onAddToCartClick
            ) {
                Icon(
                    imageVector = Icons.Sharp.Add,
                    contentDescription = null
                )
            }

            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 4.dp, end = 6.dp)
                    .size(20.dp)
                    .constrainAs(favoriteButton) {
                        top.linkTo(image.top)
                        end.linkTo(image.end)
                    },
                tint = if (dish.isFavorite) MaterialTheme.colors.primary else Color.LightGray
            )

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .constrainAs(description) {
                        top.linkTo(image.bottom)
                    }
            ) {
                Text(
                    text = stringResource(CoreR.string.dish_item_price, dish.price),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp, bottom = 5.dp)
                )
                val paddingModifier = Modifier.padding(horizontal = 3.dp)
                Divider(paddingModifier)
                Text(
                    text = dish.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .wrapContentHeight()
                )
                Divider(paddingModifier)
                Spacer(Modifier.height(3.dp))
                Divider(paddingModifier)
            }
        }

        if (dish.oldPrice > dish.price) {
            Text(
                text = stringResource(CoreR.string.dish_item_label_special_offer),
                fontSize = 10.sp,
                letterSpacing = 2.5.sp,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .wrapContentWidth(Alignment.Start)
                    .background(
                        color = Color(0xFFFFD150),
                        shape = RoundedCornerShape(topEnd = 3.dp, bottomEnd = 3.dp)
                    )
                    .padding(vertical = 3.dp, horizontal = 5.dp)
            )
        }
    }
}