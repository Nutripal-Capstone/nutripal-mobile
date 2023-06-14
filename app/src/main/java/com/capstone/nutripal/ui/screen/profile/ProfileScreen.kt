package com.capstone.nutripal.ui.screen.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.capstone.nutripal.R
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.ProfileData
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.general.shimmerEffect
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.White
import com.capstone.nutripal.ui.theme.secondText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current as ComponentActivity,
    dataStore: StoreDataUser = StoreDataUser(context),
    profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(dataStore, FakeFoodRepository())
    ),
    navController: NavController,
    ) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")
    profileViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                ProfileContent(ProfileData(), true, navController = navController)
                profileViewModel.viewModelScope.launch {
                    profileViewModel.getProfileDetail(userToken.value)
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                ProfileContent(data, false, navController = navController)
            }
            is UiState.Error -> {}
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileContent(
    data: ProfileData,
    loading : Boolean,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreDataUser(context)
    
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.h1
                    )
                },
                backgroundColor = White,
                elevation = 1.dp
            )
        },
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = data.picture,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(105.dp)
                        .shadow(1.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = data.name.toString(),
                        style = MaterialTheme.typography.body2,
                        modifier = if (loading) Modifier
                            .shimmerEffect()
                            .fillMaxWidth() else Modifier
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = data.email.toString(),
                        style = MaterialTheme.typography.body1,
                        modifier = if (loading) Modifier
                            .shimmerEffect()
                            .fillMaxWidth() else Modifier
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(27.dp),
                        border = BorderStroke(1.dp, IjoCompo),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text= "Edit Profile",
                            fontWeight = FontWeight(700)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Body Information",
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                    ) {
                        BodyInfoItem(type = "gender", value = data.gender.toString())
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "age", value = "${data.age} years old")
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "height", value = "${data.height.toString()} cm")
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "weight", value = "${data.weight.toString()} kg")
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "mealplan", value = "${data.mealsPerDay.toString()} meals per day")
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "activity", value = data.activityLevel.toString())
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "goals", value = data.goal.toString())
                        Spacer(modifier = Modifier.height(6.dp))
                        BodyInfoItem(type = "goals", value = data.dietType.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            OutlinedButton(
                onClick = {
                    scope.launch {
                        Firebase.auth.signOut()
                        googleSignInClient.signOut().await()
                        dataStore.logout()
                    }
                    navController.popBackStack()
                    navController.navigate(Screen.Welcome.route)
                },
                shape = RoundedCornerShape(27.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.error),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text= "Logout",
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
fun BodyInfoItem(
    type: String,
    value : String
) {
    var icon = Icons.Rounded.AccessibilityNew
    when(type) {
        "gender" -> icon = Icons.Rounded.Face
        "age" -> icon = Icons.Rounded.Elderly
        "mealplan" -> icon = Icons.Rounded.Fastfood
        "activity" -> icon = Icons.Rounded.DirectionsBike
        "goals" -> icon = Icons.Rounded.Stars
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = icon,
            contentDescription = "Food Search",
            tint = secondText
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            color = secondText
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
        ProfileScreen(navController = rememberNavController())
    }
}