package com.capstone.nutripal.ui.screen.welcome

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.nutripal.R
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.navigation.Screen
import com.capstone.nutripal.ui.theme.IjoCompo
import com.capstone.nutripal.ui.theme.NutriPalTheme
import com.capstone.nutripal.ui.theme.disabledText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun WelcomeScreen(navController: NavHostController) {
    val items = listOf(
        WelcomeItem(R.drawable.hydration, "Start Your Journey to Healthy Living", "Prioritize your health, embrace nutrition, fitness, and self-care."),
        WelcomeItem(R.drawable.breakfast, "Discover the Power of Nutrition and Transform Your Lifestyle", "Immerse yourself in the professional experience of meeting an AI nutritionist with the simple touch of your fingertips."),
        WelcomeItem(R.drawable.engbreakfast, "Take Control for Your Health with Personalized Nutrition", "Our app offers tailored nutrition plans and expert guidance to help you make informed choices about your needs."),
    )

    HorizontalPagerWithIndicator(
        items = items,
        navController
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithIndicator(
    items: List<WelcomeItem>,
    navController: NavHostController,
    context: Context = LocalContext.current,
    dataStore: StoreDataUser = StoreDataUser(context),
    loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(dataStore, FakeFoodRepository())
    ),
) {
    val scope = rememberCoroutineScope()
    val resultMsg by loginViewModel.result.collectAsState()

    LaunchedEffect(resultMsg) {
        when (resultMsg) {
            "success" -> {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
            "register" -> {
                navController.popBackStack()
                navController.navigate(Screen.Signup.route)
            }
        }
    }

    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("1066637915950-b22hv6f7ptb0mnrjp1maactnsrc0nmrf.apps.googleusercontent.com")
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            Firebase.auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    // User signed in successfully
                    if (idToken != null) {
                        val user = FirebaseAuth.getInstance().currentUser
                        user!!.getIdToken(true)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val idTokenSigned: String? = task.result.token
                                    if (idTokenSigned != null) {
                                        scope.launch {
                                            loginViewModel.signIn(idTokenSigned)
                                        }
                                    }
                                } else {
                                    task.exception
                                }
                            }
                    }
                } else {
                    // Authentication failed
                    Log.w("GoogleSignIn", "failed sign in")
                }
            }
        } catch (e: ApiException) {
            // Google sign-in failed
            Log.w("GoogleSignIn", "onFailure: ${e.message}")
        }
    }


    Scaffold(
        topBar = { NutripalTopAppBar() }
    ) {
        val pageCount = items.size
        val pagerState = rememberPagerState()
        var currentPage by remember { mutableStateOf(0) }

        LaunchedEffect(currentPage) {
            pagerState.animateScrollToPage(currentPage)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    count = pageCount,
                    state = pagerState,
                    modifier = Modifier.height(500.dp),
                ) { page ->
                    Column(
                        modifier = Modifier
                            .width(290.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(items[page].image),
                            contentDescription = null,
                            modifier = Modifier
                                .height(290.dp)
                                .width(290.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = items[page].title,
                            textAlign = TextAlign.Center,
                            fontSize = 21.sp,
                            fontWeight = FontWeight(700),
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = items[page].description,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .height(11.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        activeColor = IjoCompo,
                        inactiveColor = disabledText,
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (pagerState.currentPage == items.size - 1) {
                    Button(
                        onClick = {
                            scope.launch {
                                googleSignInClient.signOut().await()
                            }
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        },
                        shape = RoundedCornerShape(27.dp),
                        contentPadding = PaddingValues(horizontal = 150.dp)
                    ) {
                        Text(
                            text= "Login",
                            fontWeight = FontWeight(700)
                        )
                    }
                } else {
                    Button(
                        onClick = {
                                  currentPage = pagerState.currentPage + 1
                        },
                        shape = RoundedCornerShape(27.dp),
                        contentPadding = PaddingValues(horizontal = 150.dp)
                    ) {
                        Text(
                            text= "Next",
                            fontWeight = FontWeight(700)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(59.dp))
            }
        }
    }
}

@Composable
fun NutripalTopAppBar() {
    Box(
        modifier = Modifier
            .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 32.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.icon_horizontal),
            contentDescription = "logo nutripal",
            modifier = Modifier.height(32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}