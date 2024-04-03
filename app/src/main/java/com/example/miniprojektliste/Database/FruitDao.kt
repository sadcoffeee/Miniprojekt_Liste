package com.example.miniprojektliste.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miniprojektliste.network.Fruit

@Dao
interface FruitDao {
    @Query("SELECT * FROM fruits")
    fun getAll(): List<Fruit>

    @Query("SELECT * FROM fruits WHERE id IN (:fruitIds)")
    fun loadAllByIds(fruitIds: IntArray): List<Fruit>

    @Query("SELECT * FROM fruits WHERE name LIKE :first AND " +
            "name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Fruit

    @Query("INSERT INTO fruits VALUES(:id, :name, :cal)")
    fun addFruit(id: Int, name: String, cal: Int)

    @Insert
    fun insertAll(vararg fruits: Fruit)

    @Delete
    fun delete(Fruit: Fruit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObject(obj: Fruit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfObjects(objects: List<Fruit>)
}
