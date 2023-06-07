package com.capstone.nutripal.ui.components.badges

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White
import com.capstone.nutripal.ui.theme.disabledText

@Composable
fun StatusChips(
    title : String,
) {

    var backgroundColor = IjoCompo
    var contentColor = White
    var hasIcon = false
    var iconOnly = false

    when(title) {
        "Over The Target" -> {
            backgroundColor = Color(0xFFFFAFAF)
            contentColor = Color(0xFFF71919)
        }
        "On Target" -> {
            backgroundColor = Color(0xFFF0F7EE)
            contentColor = IjoCompo
        }
        "Eaten" -> {
            backgroundColor = IjoCompo
            contentColor = White
            hasIcon = true
        }
        "CheckForEat" -> {
            iconOnly = true
            backgroundColor = disabledText
        }
    }

    var normalPaddingModifier = Modifier.padding(start = 8.dp , top = 1.5.dp, end = if (hasIcon) 4.dp else 8.dp, bottom = 3.dp)
    var iconOnlyModifier = Modifier.padding(0.01.dp)

    NutriPalTheme() {


        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .background(backgroundColor)
//                .clickable(
//                    onClick = {
//
//                    }
//                )
        ) {
            Row(
                modifier = if(iconOnly) iconOnlyModifier else normalPaddingModifier
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                if (!iconOnly) {
                    Text(
                        text = title,
                        color = contentColor,
                        style = if (hasIcon) MaterialTheme.typography.subtitle2 else MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                }
                AnimatedVisibility(visible = hasIcon || iconOnly) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "check",
                        tint = Color.White,
                        modifier = if (hasIcon) Modifier.size(14.dp) else Modifier.size(28.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NutriPalTheme {
        Column() {
            StatusChips("Over The Target")
            StatusChips("On Target")
            StatusChips("Eaten")
            StatusChips("CheckForEat")
        }
    }
}