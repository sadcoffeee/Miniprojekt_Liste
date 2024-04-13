package com.example.miniprojektliste.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniprojektliste.network.FruitWeb
import com.example.miniprojektliste.network.FruitsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FruitViewmodel : ViewModel() {
    private val _fruits = MutableStateFlow<List<FruitWeb>>(emptyList())
    val fruits: StateFlow<List<FruitWeb>> get() = _fruits

    fun fetchFruits() {
        viewModelScope.launch {
            try {
                val fruitsList = withContext(Dispatchers.IO) {
                    FruitsApi.getFruits()
                }
                _fruits.value = fruitsList
            } catch (e: Exception) {
                e.printStackTrace()
                _fruits.value = emptyList()
            }
        }
    }
}