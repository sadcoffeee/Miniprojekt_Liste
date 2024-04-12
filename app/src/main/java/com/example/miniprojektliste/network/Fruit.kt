package com.example.miniprojektliste.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "fruits")
data class Fruit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "family") val family: String,
    @ColumnInfo(name = "order") val order: String,
    @ColumnInfo(name = "genus") val genus: String,
    @ColumnInfo(name = "amount") val amount: Int
) {
    @Entity(
        tableName = "nutritions",
        foreignKeys = [
            ForeignKey(
                entity = Fruit::class,
                parentColumns = ["id"],
                childColumns = ["fruitId"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
    data class Nutrition(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "fruitId") val fruitId: Int,
        @ColumnInfo(name = "calories") val calories: Int,
        @ColumnInfo(name = "fat") val fat: Double,
        @ColumnInfo(name = "sugar") val sugar: Double,
        @ColumnInfo(name = "carbs") val carbohydrates: Double,
        @ColumnInfo(name = "protein") val protein: Double
    )
}

@Entity(tableName = "fruitsOnline")
data class FruitOnline(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "family") val family: String,
    @ColumnInfo(name = "order") val order: String,
    @ColumnInfo(name = "genus") val genus: String
) {
    @Entity(
        tableName = "nutritionsOnline",
        foreignKeys = [
            ForeignKey(
                entity = Fruit::class,
                parentColumns = ["id"],
                childColumns = ["fruitId"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
    data class NutritionOnline(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "fruitId") val fruitId: Int,
        @ColumnInfo(name = "calories") val calories: Int,
        @ColumnInfo(name = "fat") val fat: Double,
        @ColumnInfo(name = "sugar") val sugar: Double,
        @ColumnInfo(name = "carbs") val carbohydrates: Double,
        @ColumnInfo(name = "protein") val protein: Double
    )
}