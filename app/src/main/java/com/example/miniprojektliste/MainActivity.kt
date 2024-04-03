package com.example.miniprojektliste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.miniprojektliste.Database.AppDatabase
import com.example.miniprojektliste.Database.FruitDao
import com.example.miniprojektliste.screens.AddFruitPageLayout
import com.example.miniprojektliste.ui.theme.MiniprojektListeTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val dao: FruitDao = AppDatabase.getDatabase(this).fruitDao()
        //GlobalScope.launch { AppDatabase.getDatabase(applicationContext).fruitDao().getAll() }

        setContent {
            MiniprojektListeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddFruitPageLayout(AppDatabase.getDatabase(this).fruitDao())
                }
            }
        }
    }
}
