package com.capstone.nutripal.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.DataItem
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.components.cards.HistoryFoodCard
import com.capstone.nutripal.ui.components.general.SearchBar
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navigateToDetail: (String, String) -> Unit,
    searchViewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(FakeFoodRepository())
    ),
) {
    searchViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                searchViewModel.viewModelScope.launch {
                    searchViewModel.getSearch( "")
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                SearchContent(
                    onBackClick,
                    navigateToDetail,
                    listData = data
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
    searchViewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(FakeFoodRepository())
    ),
) {

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
               searchViewModel.viewModelScope.launch {
                   searchViewModel.getSearch(it)
               }
           }
           Spacer(modifier = Modifier.height(18.dp))

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