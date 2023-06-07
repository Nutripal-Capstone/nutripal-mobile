package com.capstone.nutripal.ui.components.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.theme.White
import com.capstone.nutripal.ui.theme.defaultText
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.capstone.nutripal.ui.screen.home.HomeScreen
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.secondText


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    hint: String = "",
    isDummy: Boolean,
    onClick: () -> Unit,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val focusRequester = remember { FocusRequester() }
    var searchBarEnabledState by remember {
        mutableStateOf(!isDummy)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxWidth()
        .shadow(1.5.dp, shape = RoundedCornerShape(50.dp))
        .background(White, shape = RoundedCornerShape(50.dp))
        .padding(horizontal = 16.dp, vertical = 12.dp)
        .clickable {
            onClick()
        },
        contentAlignment = Alignment.CenterStart

    )
    {
        Icon(
            imageVector = Icons.Filled.Search,
            tint = secondText,
            contentDescription = "Food Search"
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = secondText,
                modifier = Modifier
                    .padding(start = 30.dp)

            )
        } else {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = secondText,
                contentDescription = "Search Close",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        searchBarEnabledState = false
                        text = ""
                    }
            )
        }
        if(!isDummy) {
            val keyboardController = LocalSoftwareKeyboardController.current
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearch(it)
                },
                enabled = searchBarEnabledState,
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = defaultText),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 40.dp)
                    .width(IntrinsicSize.Max)
                    .onFocusChanged {
                        isHintDisplayed = !it.isFocused
                        searchBarEnabledState = true
                        if (it.isFocused) {
                            keyboardController?.show()
                        } else {
                            keyboardController?.hide()
                        }
                    }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchBarPrev() {
    NutriPalTheme {
        SearchBar("what do you want to eat?", isDummy = true, {})
    }
}
