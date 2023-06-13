package com.capstone.nutripal.ui.components.cards

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.ui.components.badges.NutritionalChips
import com.capstone.nutripal.ui.components.badges.StatusChips
import com.capstone.nutripal.ui.components.general.FlowRow
import com.capstone.nutripal.ui.theme.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun HandleSelectableCard(
    list: List<FakeFoodClass>,
) {
    var selectedId by rememberSaveable { mutableStateOf("") }
    for (i in list) {
        SelectableCourse(
            id = i.id,
            image = i.photoUrl,
            foodTitle = i.foodTitle,
            portion = i.portion,
            cal = i.calorie,
            pro = i.protein,
            carbs= i.carbs,
            fat = i.fat,
            selected = selectedId == i.id,
        )
    }
}

@Composable
fun SelectableCourse(
    id : String,
    image : String,
    foodTitle: String,
    portion: String,
    cal : Double,
    pro : Double,
    carbs: Double,
    fat : Double,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    val cardTitle =
        when(id) {
            "1" -> "Breakfast"
            "2" -> "Lunch"
            "3" -> "Dinner"
            else -> "Snack"
        }

    NutriPalTheme() {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .background(if(selected) IjoTema.copy(0.01f) else White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, if(selected) IjoCompo else Color.LightGray, RoundedCornerShape(10.dp)),
        )
        {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .padding(10.dp, 6.dp, 10.dp, 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardTitle,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "check",
                            tint = if(selected) IjoCompo else Color.LightGray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, shadow, RoundedCornerShape(5.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier,
                        ) {
                            Text(
                                text = foodTitle,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
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
                        Spacer(modifier = Modifier.weight(1.0f))
                        FlowRow(
                            horizontalGap = 4.dp,
                            verticalGap = 4.dp,
                            alignment = Alignment.Start,
                        ) {
                            NutritionalChips("Cal", cal)
                            NutritionalChips("Pro", pro)
                            NutritionalChips("Car", carbs)
                            NutritionalChips("Fat", fat)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EatenDisabledCourse(
    image : String,
    foodTitle: String,
    portion: String,
    cal : Double,
    pro : Double,
    carbs: Double,
    fat : Double,
    modifier: Modifier = Modifier,
) {
    NutriPalTheme() {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .background(White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, shadow, RoundedCornerShape(10.dp)),
        )
        {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, shadow, RoundedCornerShape(5.dp))
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
                                    text = foodTitle,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                StatusChips(title = "Eaten")
                                Spacer(modifier = Modifier.width(4.dp))
                            }

                        }
                        Spacer(modifier = Modifier.weight(1.0f))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            NutritionalChips("Cal", cal)
                            NutritionalChips("Pro", pro)
                            NutritionalChips("Car", carbs)
                            NutritionalChips("Fat", fat)
                        }
                    }
                }
            }
        }

    }
}


@Composable
@Preview(showBackground = true)
fun SelectableFoodCardPreview() {
    NutriPalTheme {
        Column() {
            SelectableCourse(
                id = "1",
                image = "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                foodTitle = "soto",
                portion = "1 portion",
                cal = 700.0,
                pro = 700.0,
                fat = 700.0,
                carbs = 700.0,
                selected = true
            )
            SelectableCourse(
                id = "1",
                image = "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                foodTitle = "soto",
                portion = "1 portion",
                cal = 700.0,
                pro = 700.0,
                fat = 700.0,
                carbs = 700.0,
                selected = false
            )
//            EatenDisabledCourse(
//                "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
//                "Soto Ayam",
//                "1 portion",
//                cal = 700.0,
//                pro = 700.0,
//                fat = 700.0,
//                carbs = 700.0,
//            )
        }
    }
}