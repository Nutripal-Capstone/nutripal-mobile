package com.capstone.nutripal.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.HomeCardAnalysis
import com.capstone.nutripal.ui.components.general.SearchBar
import com.capstone.nutripal.ui.components.badges.StatusChips
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.theme.*
import com.capstone.nutripal.ui.theme.NutriPalTheme

@Composable
fun HomeScreen(
//    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    onSearchbarClicked: () -> Unit,
) {

    val viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFoods()
            }
            is UiState.Success -> {
                HomeContent(
                    orderFoods = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onSearchbarClicked
                )
            }
            is UiState.Error -> {}
        }
    }

}

@Composable
fun HomeContent(
    orderFoods: List<OrderFakeFood>,
    navigateToDetail: (String) -> Unit,
    onSearchbarClicked: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
       item {
           // section ijo bagian atas
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .background(IjoCompo)
           ) {
               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(16.dp)
                       .background(IjoCompo)
               ) {
                   Column(
                       modifier = Modifier
                           .fillMaxWidth()
                           .background(IjoCompo, shape = RoundedCornerShape(10.dp))
                   ) {
                       SearchBar(
                           hint = "what do you want to eat?",
                           isDummy = true,
                           onClick = onSearchbarClicked,
                       ) {
                       }
                       Spacer(modifier = Modifier
                           .fillMaxWidth()
                           .height(10.dp))
                       Row(
                           modifier = Modifier.fillMaxWidth(),
                           horizontalArrangement = Arrangement.SpaceBetween,
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Text(
                               text = "Daily Analysis",
                               color = White,
                               style = MaterialTheme.typography.body2
                           )
                           StatusChips(
                               title = "On Target"
                           )

                       }
                       Spacer(modifier = Modifier
                           .fillMaxWidth()
                           .height(5.dp))
                       HomeCardAnalysis(
                           calorie = 100,
                           calorieNeeded = 1000,
                           protein = 100,
                           proteinNeeded = 1000,
                           carbs = 100,
                           carbsNeeded = 1000,
                           fat = 100,
                           fatNeeded = 1000
                       )
                   }
               }
           }
           // section putih bagian bawah
           Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)

           ) {
               Column(modifier = Modifier.fillMaxWidth()) {
                   Text(
                       "Your Next Meal",
                       style = MaterialTheme.typography.body2
                   )
                   Spacer(modifier = Modifier.height(8.dp))
                   for(item: OrderFakeFood in orderFoods) {
                       // kalo belum dimakan masuk sini
                       if(!item.food.isEaten) {
                           HandleCourse(
                               item.food.id,
                               item.food.id,
                               item.food.photoUrl,
                               item.food.foodTitle,
                               item.food.portion,
                               item.food.isEaten,
                               item.food.calorie,
                               item.food.protein,
                               item.food.carbs,
                               item.food.fat,
                               navigateToDetail = navigateToDetail
                           )
                       }
                   }
                   Spacer(modifier = Modifier.height(16.dp))
                   Text(
                       "Eaten Meal",
                       style = MaterialTheme.typography.body2
                   )
                   Spacer(modifier = Modifier.height(8.dp))
                   for(item: OrderFakeFood in orderFoods) {
                       // kalo udah dimakan
                       if(item.food.isEaten) {
                           HandleCourse(
                               item.food.id,
                               item.food.id,
                               item.food.photoUrl,
                               item.food.foodTitle,
                               item.food.portion,
                               item.food.isEaten,
                               item.food.calorie,
                               item.food.protein,
                               item.food.carbs,
                               item.food.fat,
                               navigateToDetail = navigateToDetail
                           )
                           Spacer(modifier = Modifier.height(8.dp))
                       }
                   }
               }
           }
       }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
        HomeScreen({},{})
    }
}