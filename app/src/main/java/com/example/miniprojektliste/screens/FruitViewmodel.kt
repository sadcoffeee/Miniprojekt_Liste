package com.example.miniprojektliste.screens


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniprojektliste.network.Fruit
import com.example.miniprojektliste.network.FruitsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FruitViewmodel : ViewModel() {
    private val _fruits = MutableLiveData<List<Fruit>>()
    val fruits: LiveData<List<Fruit>> get() = _fruits

    fun fetchFruits() {
        viewModelScope.launch {
            try {
                val fruitsList = withContext(Dispatchers.IO) {
                    FruitsApi.retrofitService.getFruits()
                }
                _fruits.value = fruitsList
            } catch (e: Exception) {
                e.printStackTrace()
                _fruits.value = emptyList()
            }
        }
    }
}