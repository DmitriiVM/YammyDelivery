package com.dvm.menu.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvm.db.api.data.models.CategoryDish
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun DishItem(
    dish: CategoryDish,
    modifier: Modifier = Modifier,
    onDishClick: (dishId: String) -> Unit,
    onAddToCartClick: (dishId: String) -> Unit
) {
    Card(
        elevation = 1.dp,
        modifier = modifier
            .padding(8.dp)
            .clickable { onDishClick(dish.id) }
    ) {
        Box {
            Column {
                CoilImage(
                    data = dish.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp)), // TODO material
                    error = {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primaryVariant.copy(alpha = 0.05f)
                        )
                    }
                )
                Box {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {

                        // elevation, todo
                        Icon(
                            imageVector = Icons.Sharp.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .offset(y = (-28).dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.secondary)
                                .clickable { onAddToCartClick(dish.id) }
                        )
                    }
                    Column(
                        modifier = Modifier.padding(
                            start = 5.dp,
                            end = 5.dp,
                            top = 0.dp,
                            bottom = 5.dp
                        )
                    ) {
                        Text(
                            text = "${dish.price} ₽",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 15.dp, bottom = 5.dp)
                        )
                        Divider(modifier = Modifier.padding(horizontal = 3.dp))
                        Text(
                            text = dish.name,
                            modifier = Modifier
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .wrapContentHeight(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                        Divider(modifier = Modifier.padding(horizontal = 3.dp))
                        Spacer(modifier = Modifier.height(3.dp))
                        Divider(modifier = Modifier.padding(horizontal = 3.dp))
                    }
                }
            }
            if (dish.oldPrice > dish.price) {
                Text(
                    text = "АКЦИЯ",
                    fontSize = 10.sp,
                    letterSpacing = 2.5.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .background(
                            color = Color.Yellow.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(topEnd = 3.dp, bottomEnd = 3.dp)
                        )
                        .padding(vertical = 3.dp, horizontal = 5.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 12.dp, end = 12.dp)
                        .size(20.dp),
                    tint = if (dish.isFavorite) MaterialTheme.colors.secondary else Color.LightGray
                )
            }
        }
    }
}