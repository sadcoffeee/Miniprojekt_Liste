package com.example.miniprojektliste.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miniprojektliste.network.Fruit
import com.example.miniprojektliste.network.Nutrition

@Dao
interface FruitDao {
    @Query("SELECT * FROM fruits")
    fun getAll(): List<Fruit>

    @Query("SELECT * FROM fruits WHERE id IN (:fruitIds)")
    fun loadAllByIds(fruitIds: IntArray): List<Fruit>

    @Query("SELECT * FROM fruits WHERE name LIKE :first LIMIT 1")
    fun findByName(first: String): Fruit

    @Insert
    fun insertAll(vararg fruits: Fruit)

    @Delete
    fun delete(Fruit: Fruit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObject(obj: Fruit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObjectN(obj: Nutrition)

    suspend fun insertFruitNutritions(fruit: Fruit, nutrition: Nutrition){
        insertObject(fruit)
        insertObjectN(nutrition)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfObjects(objects: List<Fruit>)

    @Query("SELECT * FROM nutritions WHERE fruitId = :fruitId")
    suspend fun getNutritionByFruitId(fruitId: Int): Nutrition?

    @Query("SELECT fruitId FROM nutritions WHERE calories > :min AND calories < :max")
    fun sortByCals( min: Int, max: Int): List<Int>?

}
