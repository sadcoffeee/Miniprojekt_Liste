package com.example.miniprojektliste.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miniprojektliste.network.Fruit
import com.example.miniprojektliste.network.FruitOnline

@Database(
    entities = [
        Fruit::class, Fruit.Nutrition::class,
        FruitOnline::class,FruitOnline.NutritionOnline::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}
