package com.capstone.nutripal.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.capstone.nutripal.ui.components.cards.HistoryFoodCard
import com.capstone.nutripal.ui.components.general.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    SearchContent(onBackClick)
}

@Composable
fun SearchContent (
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
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
        }
        Spacer(modifier = Modifier.height(18.dp))

        repeat(5) {
            HistoryFoodCard(
                "https://media.licdn.com/dms/image/C5603AQEH6j97v2kP4A/profile-displayphoto-shrink_400_400/0/1648148613276?e=1690416000&v=beta&t=iCL-y40Z_a3BFcSssGQ304VAykVWC70FZ1DIFAA0VQ4",
                "Soto Ayam",
                "1 portion",
            )
            Spacer(modifier = Modifier.height(12.dp))
        }


    }
}