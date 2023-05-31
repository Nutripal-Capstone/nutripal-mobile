package com.capstone.nutripal.ui.screen.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.capstone.nutripal.R
import com.capstone.nutripal.ui.theme.NutriPalTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriPalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Column() {
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.h1
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "Hello $name!",
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
        Greeting2("Android")
    }
}