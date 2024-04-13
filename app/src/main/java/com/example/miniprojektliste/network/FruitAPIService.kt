package com.example.miniprojektliste.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


private const val BASE_URL = "https://www.fruityvice.com/api/fruit/"

object FruitsApi {
    private val client = OkHttpClient()

    fun getFruits(): List<FruitWeb> {

        val request = Request.Builder()
            .url(BASE_URL + "all")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val json = response.body?.string() ?: throw IOException("Empty response")

            return Gson().fromJson(json, object : TypeToken<List<FruitWeb>>() {}.type)
        }
    }
}



/* This is the code that's supposed to work but doesn't
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
*/