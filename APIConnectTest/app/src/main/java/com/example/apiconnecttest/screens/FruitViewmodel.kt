package com.example.apiconnecttest.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiconnecttest.network.Fruit
import com.example.apiconnecttest.network.FruitsApi
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
                // Handle the exception, for example log it or show an error message
                e.printStackTrace()
                _fruits.value = emptyList()
            }
        }
    }
}