package com.example.miniprojektliste.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class HomeScreen {


    @Composable
    fun CardRow(items: List<String>, amount: List<String>) {
        Row {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),


                    ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .align(Alignment.CenterHorizontally)


                    )
                    Text(
                        text = amount[index],
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)

                    )

                }
            }
        }
    }


    @Composable
    fun CardItem(name: String, rowItems: List<String>, amount: List<String>) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp)

            ) {
                Text(text = name)
                CardRow(items = rowItems, amount = amount)

            }
        }
    }


    @Composable
    fun LazyColumnWithCards(category: List<String>, rowItems: List<String>, amount: List<String>) {
        LazyColumn {
            items(category) { item ->
                CardItem(
                    name = item,
                    rowItems = rowItems,
                    amount = amount
                )

            }
        }
    }


    @Composable
    fun BottomBar() {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Gray)

            ) {

                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Red
                    ),
                    modifier = Modifier
                        .height(64.dp)
                        .width(128.dp)

                ) {
                    Text("Add item")


                }
            }
        }


    }

    @Preview
    @Composable
    fun PreviewLazyColumnWithCards() {
        val category = listOf(
            "Condiments", "Meat", "Dairy", "Eggs", "Produce", "Drinks", "Bread", "Leftovers"
        )
        val rowItems = listOf(
            "Vandmelon", "Æble", "Banan"
        )
        val amount = listOf(
            "5 kg", "10kg", "2 stk"
        )

        LazyColumnWithCards(category = category, rowItems = rowItems, amount = amount)

        BottomBar()
    }
}