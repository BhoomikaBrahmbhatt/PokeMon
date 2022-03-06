package com.android.demo.pokemon.mvvm

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface RetrofitService {

    @GET("/api/v2/pokemon")
    fun getAllPokemonName(@Query("limit") limit:Int,
                          @Query("offset") offset:Int): Call<ModelPokemon>

    @GET
    fun getPokemonNextList(@Url url: String?): Call<ModelPokemon>?


    @GET
    fun getPokemonDetails(@Url url: String?): Call<ModelPokemondetail>?

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }

}