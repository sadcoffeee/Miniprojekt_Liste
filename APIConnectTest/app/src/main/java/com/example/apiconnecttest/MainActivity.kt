package com.example.apiconnecttest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.apiconnecttest.network.Fruit
import com.example.apiconnecttest.screens.FruitViewmodel
import com.example.apiconnecttest.ui.theme.APIConnectTestTheme


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<FruitViewmodel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIConnectTestTheme {
                Scaffold {
                    MainContent(viewModel)
                }
            }
        }
        viewModel.fetchFruits()
    }
}

@Composable
fun MainContent(viewModel: FruitViewmodel) {
    val fruits by viewModel.fruits.observeAsState(initial = emptyList())

    FruitList(fruits = fruits)
}

@Composable
fun FruitList(fruits: List<Fruit>) {
    LazyColumn {
        items(fruits) { fruit ->
            FruitItem(name = fruit.name)
        }
    }
}

@Composable
fun FruitItem(name: String) {
    Text(text = name)
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    APIConnectTestTheme {

    }
}