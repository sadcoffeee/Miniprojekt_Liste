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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.Database.Navigation.Screen
import com.example.miniprojektliste.R
import com.example.miniprojektliste.network.Fruit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
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




        Button(onClick = {

            GlobalScope.launch {
                dao.insertObject(
                    Fruit(
                        name = fruitInput,
                        family = "Rosaceae",
                        order = "Rosales",
                        genus = "Fragaria",
                        amount = amountString.toIntOrNull() ?: 0
                    )
                )

                val insertedFruit = dao.findByName(fruitInput)
                val fruitId = insertedFruit.id

                val nutritionToAdd = Fruit.Nutrition(
                    fruitId = fruitId,
                    calories = amountString.toIntOrNull() ?: 0,
                    fat = 0.4,
                    sugar = 5.2,
                    carbohydrates = 5.5,
                    protein = 0.8
                )
                dao.insertObjectN(nutritionToAdd)
            }
            navController.navigate(Screen.HomeScreen.route)
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

