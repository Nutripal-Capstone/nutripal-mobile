package com.capstone.nutripal.ui.components.cards

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.capstone.nutripal.ui.components.badges.NutritionalChips
import com.capstone.nutripal.ui.components.badges.StatusChips
import com.capstone.nutripal.ui.components.general.FlowRow
import com.capstone.nutripal.ui.theme.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun HandleCourse(
    foodId : String,
    cardTitle : String,
    image : String,
    foodTitle: String,
    portion: String,
    isEaten: Boolean,
    cal : String,
    pro : String,
    carbs: String,
    fat : String,
    navigateToDetail : (String) -> Unit,
    onEat : () -> Unit,
    onUneat : () -> Unit,
    onDelete : () -> Unit,
) {
    var eatStatus by rememberSaveable { mutableStateOf(isEaten) }
    val eatFunction = {
        eatStatus = true
        onEat()
    }
    val unEatFunction = {
        eatStatus = false
        onUneat()
    }
    val makan = SwipeAction(
        icon = {
            Text(
            "Eat",
                color = White,
                modifier = Modifier.padding(start = 20.dp)
        ) },
        background = IjoCompo,
        onSwipe = {
            eatFunction()
            println("makan" + eatStatus)
        }
    )
    val unmakan = SwipeAction(
        icon = {
            Text(
                "Un-Eat",
                color = White,
                modifier = Modifier.padding(end = 20.dp)
            ) },
        background = Color.Red,
        isUndo = true,
        onSwipe = {
            unEatFunction()
            println("unmakan" + eatStatus)
        },
    )

    SwipeableActionsBox(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        swipeThreshold = 100.dp,
        startActions = if (eatStatus) listOf(unmakan) else emptyList(),
        endActions = if (!eatStatus) listOf(makan) else emptyList()
    ) {
        if(eatStatus) {
            println("eaten")
            EatenCourse(
                image = "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                foodTitle = foodTitle,
                portion = portion,
                cal = cal,
                pro = pro,
                carbs= carbs,
                fat = fat,
                modifier = Modifier.clickable {
                    navigateToDetail(foodId)
                    println(foodId)
                },
                isEaten = true,
                onEat = { eatFunction() },
                onUneat = { unEatFunction() },
                onDelete = { onDelete() },
            )
        } else if(!eatStatus) {
            println("not eaten")
            EatenCourse(
                image = "https://cdn.discordapp.com/attachments/1000437373240361102/1118062814079234058/no-image.png",
                foodTitle = foodTitle,
                portion = portion,
                cal = cal,
                pro = pro,
                carbs= carbs,
                fat = fat,
                modifier = Modifier.clickable {
                    navigateToDetail(foodId)
                },
                isEaten = false,
                onEat = { eatFunction() },
                onUneat = { unEatFunction() },
                onDelete = { onDelete() },
            )
//            MainCourse(
//                cardTitle = cardTitle,
//                image = image,
//                foodTitle = foodTitle,
//                portion = portion,
//                isEaten = isEaten,
//                cal = cal,
//                pro = pro,
//                carbs= carbs,
//                fat = fat,
//                modifier = Modifier.clickable {
//                    navigateToDetail(foodId)
//                }
//            )
        }
    }

}

@Composable
fun MainCourse(
    cardTitle : String,
    image : String,
    foodTitle: String,
    portion: String,
    isEaten: Boolean,
    cal : String,
    pro : String,
    carbs: String,
    fat : String,
    modifier: Modifier = Modifier,
) {

    // dropdown more
    val isMenuOpen = remember { mutableStateOf(false) }
    val menuItems = listOf("Re-generate", "Eat", "Un-Eat")
    val selectedItem = remember { mutableStateOf("") }

    NutriPalTheme() {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .background(White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, shadow, RoundedCornerShape(10.dp)),
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
                        if(isEaten) StatusChips(title = "Eaten") else null
                        Spacer(modifier = Modifier.width(4.dp))

                        // for the dropdown
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                            tint = secondText,
                            modifier = Modifier
                                .clickable { isMenuOpen.value = true }
                        )
                        DropdownMenu(
                            expanded = isMenuOpen.value,
                            onDismissRequest = { isMenuOpen.value = false },
                            modifier = Modifier.border(0.5.dp, shadow, RectangleShape)
                        ) {
                            menuItems.forEach { item ->
                                DropdownMenuItem(onClick = {
                                    selectedItem.value = item
                                    isMenuOpen.value = false
                                }) {
                                    Text(item)
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
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
//                            verticalAlignment = Alignment.Bottom
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
fun EatenCourse(
    image : String,
    foodTitle: String,
    portion: String,
    cal : String,
    pro : String,
    carbs: String,
    fat : String,
    isEaten: Boolean,
    modifier: Modifier = Modifier,
    onEat : () -> Unit,
    onUneat : () -> Unit,
    onDelete : () -> Unit,
) {
    // dropdown more
    val isMenuOpen = remember { mutableStateOf(false) }
    val menuItemsMealPlan = listOf("Eat", "Delete")
    val menuItemsEaten = listOf("Un-Eat")
    val selectedItem = remember { mutableStateOf("") }

    when(selectedItem.value) {
        ("Eat") -> {
            onEat()
            selectedItem.value = ""
        }
        ("Un-Eat") -> {
            onUneat()
            selectedItem.value = ""
        }
        ("Delete") -> {
            onDelete()
            selectedItem.value = ""
        }

    }

    NutriPalTheme() {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .background(if(isEaten) Color(0xFFCCE4D0) else White, shape = RoundedCornerShape(12.dp))
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
//                                modifier = Modifier.width(70.dp),
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                if (isEaten) {
                                    StatusChips(title = "Eaten")
                                }
                                Spacer(modifier = Modifier.width(4.dp))

                                // for the dropdown
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = null,
                                    tint = secondText,
                                    modifier = Modifier
                                        .clickable { isMenuOpen.value = true }
                                )
                                DropdownMenu(
                                    expanded = isMenuOpen.value,
                                    onDismissRequest = { isMenuOpen.value = false },
                                    modifier = Modifier.border(0.5.dp, shadow, RectangleShape)
                                ) {
                                    (if(isEaten) menuItemsEaten else menuItemsMealPlan).forEach { item ->
                                        DropdownMenuItem(onClick = {
                                            selectedItem.value = item
                                            isMenuOpen.value = false
                                        }) {
                                            Text(item)
                                        }
                                    }
                                }
                            }

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
@Preview(showBackground = true)
fun FoodCardAnalysis() {
    NutriPalTheme {
        Column() {
//            MainCourse(
//                cardTitle = "Breakfast",
//                image = "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
//                foodTitle = "soto",
//                portion = "1",
//                isEaten = true,
//                cal = 70.0,
//                carbs = 120.0,
//                fat = 50.0,
//                pro = 20.0
//            )
//            EatenCourse( "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4", "Soto Ayam", "1 portion", )
        }
//        TestGrid()
    }
}
