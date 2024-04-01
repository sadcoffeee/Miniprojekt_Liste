package com.example.apiconnecttest.network
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


private const val BASE_URL =
    "https://www.fruityvice.com/api/fruit/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object FruitsApi {
    val retrofitService : FruitApiService by lazy {
        retrofit.create(FruitApiService::class.java)
    }
}

interface FruitApiService {
    @GET("all")
    suspend fun getFruits(): List<Fruit>
}
