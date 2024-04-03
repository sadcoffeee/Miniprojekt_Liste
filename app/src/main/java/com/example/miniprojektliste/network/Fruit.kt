package com.example.miniprojektliste.network
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "fruits")
data class Fruit(
    @ColumnInfo(name = "name") val name: String,
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "family") val family: String,
    @ColumnInfo(name = "order") val order: String,
    @ColumnInfo(name = "genus") val genus: String,
    @ColumnInfo(name = "nutritions") val nutritions: Nutrition
) {
    data class Nutrition(
        @ColumnInfo(name = "calories") val calories: Int,
        @ColumnInfo(name = "fat") val fat: Double,
        @ColumnInfo(name = "sugar") val sugar: Double,
        @ColumnInfo(name = "carbs") val carbohydrates: Double,
        @ColumnInfo(name = "protein") val protein: Double
    )
}