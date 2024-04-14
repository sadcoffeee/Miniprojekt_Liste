package com.example.miniprojektliste.screens

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.Database.Navigation.Screen
import com.example.miniprojektliste.R
import com.example.miniprojektliste.network.Fruit
import com.example.miniprojektliste.network.FruitWeb
import com.example.miniprojektliste.network.FruitsApi
import com.example.miniprojektliste.network.NutritionsWeb
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddFruitPageLayout(
    context: Context,
    viewModel: FruitViewmodel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val dao: FruitDao = AppDatabase.getDatabase(context).fruitDao()
    var fruitInput by remember { mutableStateOf("") }
    var amountString by remember { mutableStateOf("") }
    var webFruitsList by remember { mutableStateOf<List<FruitWeb>>(emptyList()) }
    var wrongFruit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val job1 = launch {
            webFruitsList = withContext(Dispatchers.IO) {
                FruitsApi.getFruits()
            }
        }

        job1.join()
    }

    //Dropdown meny
    var isExpanded by remember { mutableStateOf(false) }
    var filteredFruits by remember { mutableStateOf(webFruitsList) }
    //Data to save after item selected
    var fruitName by remember { mutableStateOf("") }
    var DDFruitId by remember { mutableIntStateOf(0) }
    var fruitFamily by remember { mutableStateOf("") }
    var fruitOrder by remember { mutableStateOf("") }
    var fruitGenus by remember { mutableStateOf("") }
    var fruitCalories by remember { mutableIntStateOf(0) }
    var fruitFat by remember { mutableDoubleStateOf(0.0) }
    var fruitSugar by remember { mutableDoubleStateOf(0.0) }
    var fruitCarbonhydrates by remember { mutableDoubleStateOf(0.0) }
    var fruitProtein by remember { mutableDoubleStateOf(0.0) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (wrongFruit) {
            WrongFruitText()
        }

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            TextField(
                value = fruitInput,
                onValueChange = {
                    fruitInput = it
                    isExpanded = true
                },
                readOnly = false,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                webFruitsList.forEach { fruit ->
                    if (fruit.name.contains(fruitInput, ignoreCase = true)) {
                        DropdownMenuItem(
                            text = { Text(fruit.name) },
                            onClick = {
                                fruitInput = fruit.name
                                fruitName = fruit.name
                                DDFruitId = fruit.id
                                fruitFamily = fruit.family
                                fruitOrder = fruit.order
                                fruitGenus = fruit.genus
                                fruitCalories = fruit.nutritions.calories
                                fruitFat = fruit.nutritions.fat
                                fruitSugar = fruit.nutritions.sugar
                                fruitCarbonhydrates = fruit.nutritions.carbohydrates
                                fruitProtein = fruit.nutritions.protein
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = modifier.height(32.dp))
        EditTextField(
            label = R.string.amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = amountString,
            onValueChanged = { amountString = it }
        )
        Spacer(modifier = modifier.height(32.dp))


        Button(onClick = {

            if (checkInput(list = webFruitsList, stringToCheck = fruitInput)){
                GlobalScope.launch {
                    dao.insertObject(
                        Fruit(
                            name = fruitName,
                            family = fruitFamily,
                            order = fruitOrder,
                            genus = fruitGenus,
                            amount = amountString.toIntOrNull() ?: 0
                        )
                    )

                    val insertedFruit = dao.findByName(fruitInput)
                    val fruitId = insertedFruit.id

                    val nutritionToAdd = Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = fruitCalories,
                        fat = fruitFat,
                        sugar = fruitSugar,
                        carbohydrates = fruitCarbonhydrates,
                        protein = fruitProtein
                    )
                    dao.insertObjectN(nutritionToAdd)
                }
                navController.navigate(Screen.HomeScreen.route)
            }
            else {
                wrongFruit = true
            }
        }
        ) {
            Text(text = stringResource(R.string.btn_text))
        }
    }
    BottomBar(navController)
}

@Composable
fun EditTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        label = { Text(stringResource(label)) },
        onValueChange = onValueChanged,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Composable
fun WrongFruitText(){
    Text(
        text = stringResource(R.string.wrong_fruit),
        color = Color.Red,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .padding(8.dp)
    )
}

fun checkInput(list: List<FruitWeb>, stringToCheck: String): Boolean{
    for (i in list){
        if (stringToCheck == i.name){
            return true
            break
        }
    }
    return false
}
