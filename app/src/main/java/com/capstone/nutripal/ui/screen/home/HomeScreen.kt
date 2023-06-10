package com.capstone.nutripal.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.capstone.nutripal.R
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun HomeScreen(
    name: String,
    navController: NavController
) {
    Greeting2(name = name, navController = navController)
}

@Composable
fun Greeting2(
    name: String,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreDataUser(context)

    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

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
        Button(
            onClick = {
                scope.launch {
//                    Firebase.auth.signOut()
                    googleSignInClient.signOut().await()
                    dataStore.logout()
                }
                navController.popBackStack()
                navController.navigate(Screen.Welcome.route)
            },
            shape = RoundedCornerShape(27.dp),
            contentPadding = PaddingValues(horizontal = 150.dp)
        ) {
            Text(
                text= "Logout",
                fontWeight = FontWeight(700)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
        Greeting2("Android", navController = rememberNavController())
    }
}