package com.capstone.nutripal.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.nutripal.R
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.theme.NutriPalTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    val context = LocalContext.current
    val dataStore = StoreDataUser(context)
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")

    // Delay for 2 seconds and navigate to the next screen
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
//        if (userToken.value == "") {
//            navController.navigate(Screen.Welcome.route)
//        } else {
//            navController.navigate(Screen.Home.route)
//        }
        navController.navigate(Screen.Home.route)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_only),
                contentDescription = "Logo Nutripal",
                modifier = Modifier
                    .size(240.dp)
                    .padding(bottom = 20.dp)
            )
            Row {
                Text(
                    text = "NUTRI",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight(700)
                    )
                )
                Text(
                    text = "PAL",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight(400)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NutriPalTheme {
        SplashScreen(navController = rememberNavController())
    }
}