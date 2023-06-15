package com.capstone.nutripal.ui.screen.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutripal.data.FakeFoodRepository
import com.capstone.nutripal.model.FakeFoodClass
import com.capstone.nutripal.model.FakeFoodData
import com.capstone.nutripal.model.OrderFakeFood
import com.capstone.nutripal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FakeFoodRepository
) : ViewModel() {

}