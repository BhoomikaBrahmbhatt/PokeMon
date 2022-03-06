package com.android.demo.pokemon.mvvm


class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllPokemonNameList(limit: Int,offset: Int) = retrofitService.getAllPokemonName(limit,offset)

    fun getPokemonDetails(url: String) = retrofitService.getPokemonDetails(url)

    fun getPokemonNextList(url: String) = retrofitService.getPokemonNextList(url)
}