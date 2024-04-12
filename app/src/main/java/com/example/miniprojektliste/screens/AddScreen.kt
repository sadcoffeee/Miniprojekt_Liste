package com.example.miniprojektliste.screens

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.R
import com.example.miniprojektliste.network.Fruit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AddFruitPageLayout(
    context: Context,
    viewModel: FruitViewmodel,
    modifier: Modifier = Modifier
) {
    val dao: FruitDao = AppDatabase.getDatabase(context).fruitDao()
    var fruitInput by remember { mutableStateOf("") }
    var amountString by remember { mutableStateOf("") }

    // Temporary code to make a quick call to the API
    val fruits by viewModel.fruits.observeAsState(initial = "no thing yet")

    Text(text = fruits.toString())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        EditTextField(
            label = R.string.choose_item_tx,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = fruitInput,
            onValueChanged = { fruitInput = it }
        )
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

        var listIndex by remember { mutableIntStateOf(0) }
        val addFruits = listOf(
            Fruit(
                name = "Strawberry",
                family = "Rosaceae",
                order = "Rosales",
                genus = "Fragaria",
                amount = 1
            ),
            Fruit(
                name = "Banana",
                family = "Musaceae",
                order = "Zingiberales",
                genus = "Musa",
                amount = 1
            ),
            Fruit(
                name = "Tomato",
                family = "Solanaceae",
                order = "Solanales",
                genus = "Solanum",
                amount = 1
            ),
            Fruit(
                name = "Pear",
                family = "Rosaceae",
                order = "Rosales",
                genus = "Pyrus",
                amount = 1
            ),
            Fruit(
                name = "Fig",
                family = "Moraceae",
                order = "Rosales",
                genus = "Ficus",
                amount = 1
            ),
            Fruit(
                name = "Orange",
                family = "Rutaceae",
                order = "Sapindales",
                genus = "Citrus",
                amount = 1
            ),
        )


        Button(onClick = {

            GlobalScope.launch {
                val fruitToAdd = addFruits[listIndex]
                dao.insertObject(fruitToAdd)

                val insertedFruit = dao.findByName(addFruits[listIndex].name)
                val fruitId = insertedFruit.id

                val addNutrition = listOf(
                    Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = 81,
                        fat = 0.4,
                        sugar = 5.4,
                        carbohydrates = 5.5,
                        protein = 0.8
                    ),
                    Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = 96,
                        fat = 0.2,
                        sugar = 17.2,
                        carbohydrates = 22.0,
                        protein = 1.0
                    ),
                    Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = 74,
                        fat = 0.2,
                        sugar = 2.6,
                        carbohydrates = 3.9,
                        protein = 0.9
                    ),
                    Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = 57,
                        fat = 0.1,
                        sugar = 10.0,
                        carbohydrates = 15.0,
                        protein = 0.4
                    ),
                    Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = 74,
                        fat = 0.3,
                        sugar = 16.0,
                        carbohydrates = 19.0,
                        protein = 0.8
                    ),
                    Fruit.Nutrition(
                        fruitId = fruitId,
                        calories = 43,
                        fat = 0.2,
                        sugar = 8.2,
                        carbohydrates = 8.3,
                        protein = 1.0
                    ),
                )

                val nutritionsToAdd = addNutrition[listIndex]
                dao.insertObjectN(nutritionsToAdd)
                listIndex++
            }
        }
        ) {
            Text(text = stringResource(R.string.btn_text))
        }
    }
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

