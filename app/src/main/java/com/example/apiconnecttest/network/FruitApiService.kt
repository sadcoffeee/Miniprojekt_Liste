package com.example.apiconnecttest.network
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET


private const val BASE_URL =
    "https://www.fruityvice.com/api/fruit/"

private val okHttpClient = OkHttpClient.Builder()
    .build()


private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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
