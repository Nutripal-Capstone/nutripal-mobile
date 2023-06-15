package com.capstone.nutripal.ui.screen.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.di.Injection
import com.capstone.nutripal.model.StoreDataUser
import com.capstone.nutripal.ui.ViewModelFactory
import com.capstone.nutripal.ui.common.UiState
import com.capstone.nutripal.ui.theme.NutriPalTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.model.FakeFoodData
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.components.cards.HomeCardAnalysis
import com.capstone.nutripal.ui.components.general.SearchBar
import com.capstone.nutripal.ui.components.badges.StatusChips
import com.capstone.nutripal.ui.components.cards.HandleCourse
import com.capstone.nutripal.ui.theme.*

@Composable
fun HomeScreen(
//    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    onSearchbarClicked: () -> Unit,
    navigateToMealPlan: () -> Unit,
    context: Context = LocalContext.current,
    dataStore: StoreDataUser = StoreDataUser(context)
) {
}

@Composable
fun HomeContent(
    orderFoods: List<OrderFakeFood>,
    navigateToDetail: (String) -> Unit,
    onSearchbarClicked: () -> Unit,
    onSeeMoreClicked: () -> Unit,
    homeViewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
                // isi ya ki

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NutriPalTheme {
//        HomeScreen({},{})
    }
}