package com.example.apiconnecttest.network
import kotlinx.serialization.Serializable


@Serializable
data class Fruit(
    val name: String,
    val id: Int,
    val family: String,
    val order: String,
    val genus: String,
    val nutritions: Nutrition
) {
    @Serializable
    data class Nutrition(
        val calories: Int,
        val fat: Double,
        val sugar: Double,
        val carbohydrates: Double,
        val protein: Double
    )
}
