package com.capstone.nutripal.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.capstone.nutripal.ui.components.badges.NutritionalChips
import com.capstone.nutripal.ui.components.general.shimmerEffect
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White
import com.capstone.nutripal.ui.theme.secondText
import com.capstone.nutripal.ui.theme.shadow

@Composable
fun ShimmeerFoodHistory() {
    NutriPalTheme() {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(White)
        )
        {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)

                ) {
                    AsyncImage(
                        model = "",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, shadow, RoundedCornerShape(5.dp))
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                verticalAlignment = Alignment.Bottom,

                                ) {
                                Text(
                                    text = "",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shimmerEffect()
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "",
                                    style = MaterialTheme.typography.subtitle2,
                                    color = secondText,
                                    modifier = Modifier.padding(2.dp).fillMaxWidth(0.5f).shimmerEffect()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1.0f))

                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    modifier = Modifier.height(1.dp),
                    color = shadow
                )
            }
        }
    }
}

@Composable
fun ShimmerFoodCardEaten(
) {
    NutriPalTheme() {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .background(White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, shadow, RoundedCornerShape(10.dp)),
        )
        {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    AsyncImage(
                        model = "image",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, shadow, RoundedCornerShape(5.dp))
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = "",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.fillMaxWidth().shimmerEffect()
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.subtitle2,
                                    modifier = Modifier.fillMaxWidth().shimmerEffect()
                                )

                            }

                        }

                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    NutriPalTheme {
//        ShimmerFoodCardEaten()
        ShimmeerFoodHistory()
    }
}