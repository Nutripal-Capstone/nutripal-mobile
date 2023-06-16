package com.capstone.nutripal.ui.screen.search

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.R
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.model.DataItem
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.HistoryFoodCard
import com.capstone.nutripal.ui.components.cards.ShimmeerFoodHistory
import com.capstone.nutripal.ui.components.general.SearchBar
import com.capstone.nutripal.ui.screen.detail.DetailPageViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navigateToDetail: (String, String) -> Unit,
    context: Context = LocalContext.current as ComponentActivity,
    dataStore: StoreDataUser = StoreDataUser(context),
    searchViewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(
            dataStore, FakeFoodRepository()
        )
    ),
) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")

    searchViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                if (userToken.value != "" )
                searchViewModel.viewModelScope.launch {
                    searchViewModel.getSearch( userToken.value, "")
                }
                SearchContent(
                    onBackClick,
                    navigateToDetail,
                    listData = listOf(),
                    isLoading = true
                )
            }
            is UiState.Success -> {
                val data = uiState.data
                SearchContent(
                    onBackClick,
                    navigateToDetail,
                    listData = data,
                    isLoading = false
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun SearchContent (
    onBackClick: () -> Unit,
    navigateToDetail: (String, String) -> Unit,
    listData : List<DataItem>,
    context: Context = LocalContext.current as ComponentActivity,
    dataStore: StoreDataUser = StoreDataUser(context),
    searchViewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(dataStore, FakeFoodRepository())
    ),
    isLoading: Boolean,
) {
    val userToken = dataStore.getUserJwtToken().collectAsState(initial = "")
    var textValue by remember { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
       item {
           Row(
               modifier = Modifier.fillMaxWidth()
           ) {
               Icon(
                   imageVector = Icons.Default.ArrowBack,
                   contentDescription = "Arrow Back",
                   modifier = Modifier
                       .clickable { onBackClick() }
               )
               Spacer(modifier = Modifier.width(10.dp))
               Text(
                   "what do you want to eat?",
                   style = MaterialTheme.typography.body2
               )
           }
           Spacer(modifier = Modifier.height(12.dp))
           SearchBar(
               hint = "search food...",
               isDummy = false,
               onClick = {},
           ) {
               textValue = it
               if (userToken.value != "") {
                   searchViewModel.viewModelScope.launch {
                       searchViewModel.getSearch(userToken.value, it)
                   }
               }
           }
           Spacer(modifier = Modifier.height(18.dp))

           if(isLoading) {
               repeat(5) {
                   ShimmeerFoodHistory()
               }
           } else {
               if (listData.isEmpty() && textValue != "") {
                   Column (
                       modifier = Modifier.fillMaxWidth(),
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       Image(
                           painter = painterResource(id = R.drawable.not_found_bro),
                           contentDescription = "Not Found Meal Plan",
                           modifier = Modifier
                               .size(180.dp)
                       )
                       Spacer(modifier = Modifier.height(10.dp))
                       Text(
                           text = "Oops, Meal Not Found...",
                           style = MaterialTheme.typography.h1
                       )
                   }
               } else {
                   for(i in listData) {
                       HistoryFoodCard(
                           "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                           i.foodName.toString(),
                           i.servingDescription.toString(),
                           i.calories,
                           i.protein,
                           i.carbohydrate,
                           i.fat,
                           modifier = Modifier.clickable {
                               i.foodId?.let { i.servingId?.let { it1 -> navigateToDetail(i.foodId, it1) } }
                           }
                       )
                       Spacer(modifier = Modifier.height(12.dp))
                   }
               }
           }

       }


    }
}