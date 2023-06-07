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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.capstone.nutripal.ui.components.badges.NutritionalChips
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White
import com.capstone.nutripal.ui.theme.secondText
import com.capstone.nutripal.ui.theme.shadow

@Composable
fun HistoryFoodCard(
    image : String,
    foodTitle: String,
    portion: String,
    modifier: Modifier = Modifier,
) {

    NutriPalTheme() {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .background(White)
        )
        {
            Column(
                modifier = modifier
                    .wrapContentHeight()
//                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    AsyncImage(
                        model = "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, shadow, RoundedCornerShape(5.dp))
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
                                modifier = Modifier,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = foodTitle,
                                    style = MaterialTheme.typography.body1
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "($portion)",
                                    style = MaterialTheme.typography.subtitle2,
                                    color = secondText,
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1.0f))
//                        LazyVerticalGrid(
//                            columns = GridCells.Adaptive(67.dp),
//                            verticalArrangement = Arrangement.spacedBy(4.dp),
//                            horizontalArrangement = Arrangement.spacedBy(4.dp),
//                            content = {
//                                val listOfChips = listOf(
//                                    "Cal",
//                                    "Car",
//                                    "Pro",
//                                    "Fat"
//                                )
//                                val listOfValues = listOf(
//                                    700,
//                                    700,
//                                    700,
//                                    700
//                                )
//                                items(4) { i ->
//                                    NutritionalChips(listOfChips[i], listOfValues[i])
//                                }
//                            }
//                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            NutritionalChips("Cal", 700.0)
                            NutritionalChips("Pro", 700.0)
                            NutritionalChips("Car", 700.0)
                            NutritionalChips("Fat", 700.0)
                        }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    NutriPalTheme {
        HistoryFoodCard( "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4", "Soto Ayam", "1 portion", )
    }
}