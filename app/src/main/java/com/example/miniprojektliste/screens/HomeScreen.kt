package com.example.miniprojektliste.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.MainActivity

class HomeScreen {

    @Composable
    fun GridOfFruitsItem(itemsDisplay: List<Int>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items(100) { i ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(text = "display navnet fra listen")
                        Text(text = "Display amount fra listen")
                        Text(text = "Display Calories fra listen")

                    }
                }
            }
        )
    }





    @Composable
    fun LazyColumnWithCards(
        category: List<String>,
        items: List<List<Int>>,
    ) {
        var index by remember { mutableIntStateOf(0) }

        LazyColumn {
            index = 0
            items(category) { item ->
                Card() {
                    GridOfFruitsItem(itemsDisplay = items[index] )
                    index++
                }
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
            "under 70 calories", "between 70 and 90 calories", "over 90 calories",
            )
        val rowItems = listOf(
            "Vandmelon", "Æble", "Banan"
        )
        val amount = listOf(
            "5 kg", "10kg", "2 stk"
        )

        //LazyColumnWithCards(category = categorys, items = listOf(70,31,75,94))

        BottomBar()
    }
}


