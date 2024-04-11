package com.example.miniprojektliste.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.format.TextStyle

class HomeScreen {

       @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun LazyColumnWithCards(
        category: List<String>,
        context: Context
    ) {
        val dao: FruitDao = AppDatabase.getDatabase(context).fruitDao()

        var index by remember { mutableIntStateOf(0) }
        var itemList by remember { mutableStateOf<List<List<Int>>>(emptyList()) }
        LaunchedEffect(Unit){
            itemList = listOf(
                dao.sortByCals(0,70),
                dao.sortByCals(70,100),
                dao.sortByCals(0,300)
            )
            Log.d("database itesms", itemList.toString())
        }

        LazyColumn {
            items(itemList.size) { item ->
                Card (
                    modifier = Modifier
                        .background(Color.Yellow)
                        .fillMaxWidth()
                        .height(1000.dp)
                        .wrapContentSize()
                        .padding(16.dp)
                ){
                    GridOfFruitsItem(itemList = itemList[item], context = context)
                }
            }
        }
    }


    @Composable
    fun GridOfFruitsItem(itemList: List<Int>, context: Context) {

        val dao: FruitDao = AppDatabase.getDatabase(context).fruitDao()

        var name: String by remember { mutableStateOf("") }
        var cal: Int by remember { mutableIntStateOf(0) }

        Card (
            modifier = Modifier
                .background(Color.Red)
                .fillMaxSize()
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(itemList.size) { item ->
                    LaunchedEffect(Unit){
                        name = dao.findFruitNameById(itemList[item])
                        cal = dao.findFruitCalById(itemList[item])
                    }

                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.Green)
                    ){
                        Column {
                            Text(text = name, fontSize = 32.sp)
                            Text(text = cal.toString(), fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }




    @Preview(
        showSystemUi = true
    )
    @Composable
    fun HomeScreenPage(
        context: Context = LocalContext.current,
        modifier: Modifier = Modifier
    ) {
        LazyColumnWithCards(
            category = listOf(
            "under 70 calories", "between 70 and 90 calories", "over 90 calories",
            ),
            context = context
        )
    }


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
}


