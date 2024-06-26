package com.example.miniprojektliste.screens

import android.content.Context
import android.text.AutoText
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.widget.TextViewCompat.AutoSizeTextType
import androidx.navigation.NavController
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.Database.Navigation.Screen
import com.example.miniprojektliste.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import androidx.room.util.copy


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
                dao.sortByCals(70,90),
                dao.sortByCals(90,300)
            )
            Log.d("database itesms", itemList.toString())
        }

        LazyColumn {
            items(itemList.size) { item ->
                Card (
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .fillMaxWidth()
                        .height(calculateCardHeight(itemList[item]))
                        .wrapContentSize()
                        .padding(16.dp)
                ){
                    Text(
                        text = category[item],
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .offset(x = 10.dp)
                    )
                    GridOfFruitsItem(itemList = itemList[item], context = context)
                }
            }
        }
    }


    @Composable
    fun GridOfFruitsItem(itemList: List<Int>, context: Context) {

        val dao: FruitDao = AppDatabase.getDatabase(context).fruitDao()
        //var fruitName by remember { mutableStateOf("") }
        //var fruitCal by remember { mutableIntStateOf(0) }
        var fruitList by remember { mutableStateOf<List<String>>(emptyList()) }
        var calList by remember { mutableStateOf<List<Int>>(emptyList()) }
        var amountList by remember { mutableStateOf<List<Int>>(emptyList()) }

        LaunchedEffect(Unit) {
            val job = launch {
                for (i in itemList) {
                    fruitList = fruitList.toMutableList().apply { add(dao.findFruitNameById(i)) }
                }
            }
            val job2 = launch {
                for (i in itemList) {
                    calList = calList.toMutableList().apply { add(dao.findFruitCalById(i)) }
                }
            }
            val job3 = launch {
                for (i in itemList) {
                    amountList = amountList.toMutableList().apply { add(dao.findFruitAmountById(i)) }
                }
            }
            job.join()
            job2.join()
            job3.join()
        }

        Card (
            modifier = Modifier
                .fillMaxSize()
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(itemList.size) { item ->
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.Gray)
                    ){
                        //val (fruitName, fruitCal) = FruitViewmodel().getFruitDetails(itemList[item], dao)

                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            AutoResizedText(
                                text = fruitList.getOrNull(item) ?: "Loading...",
                                )

                            Text(
                                text = calList.getOrNull(item)?.toString() ?: "",
                                fontSize = 12.sp,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = amountList.getOrNull(item)?.toString() ?: "",
                                fontSize = 12.sp,
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    fun calculateCardHeight(itemList: List<Int>): Dp {
        //calculate the nuber of rows in the grid based on the number of items
        val numRows = (itemList.size + 2) / 3 //assuming 3 items per row

        //calculate the height of each row, adjust if we need
        val rowHeight = 180.dp

        if (numRows > 1) {
            //Calculate the total height of the card minus the height of the category
            val totalHeight = numRows * rowHeight - 65.dp //new
            return totalHeight
        }

        else {
            val totalHeight = numRows * rowHeight
            return totalHeight
        }
    }



    @Composable
    fun HomeScreenPage(
        context: Context = LocalContext.current,
        navController: NavController,
        modifier: Modifier = Modifier
    ) {
        LazyColumnWithCards(
            category = listOf(
            "under 70 calories", "between 70 and 90 calories", "over 90 calories",
            ),
            context = context
        )
        BottomBar(navController)
    }


    }

@Composable
fun AutoResizedText(
    text: String,
    fontSize: TextUnit = 28.sp,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    var currentFontSize by remember {
        mutableStateOf(fontSize)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }
    val defaultFontSize = 28.sp

    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if ( shouldDraw) {
                drawContent()
            }
        },
        softWrap = false, //makes sure only one line
        style = TextStyle(
            fontSize = currentFontSize
        ),
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if(currentFontSize.isUnspecified){
                    currentFontSize = defaultFontSize
                }
                    currentFontSize *= 0.95
            }
            else {
               shouldDraw = true
            }
        }
    )
}
    @Composable
    fun BottomBar(
        navController: NavController
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Gray),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(
                    onClick = { navController.navigate(Screen.HomeScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(64.dp)
                        .width(128.dp),


                    ) {
                    Text(
                        "Home Screen",
                        textAlign = TextAlign.Center
                    )
                }


                Button(
                    onClick = { navController.navigate(Screen.AddScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
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
        






