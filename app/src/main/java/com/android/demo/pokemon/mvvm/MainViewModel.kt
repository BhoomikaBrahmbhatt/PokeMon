package com.android.demo.pokemon.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.demo.pokemon.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val charactersList = MutableLiveData<ModelPokemon>()
    val errorMessage = MutableLiveData<String>()

    val characterDetail = MutableLiveData<ModelPokemondetail>()
    val errorMessageDetail = MutableLiveData<String>()

    fun getPokemondetail(url: String) {
        repository.getPokemonDetails(url)!!.enqueue(object :
            Callback<ModelPokemondetail> {
            override fun onResponse(
                call: Call<ModelPokemondetail>,
                response: Response<ModelPokemondetail>
            ) {
                characterDetail.postValue(response.body())
            }

            override fun onFailure(call: Call<ModelPokemondetail>, t: Throwable) {
                errorMessageDetail.postValue(t.toString())
            }

        })
    }

    fun getPokemonlist(limit: Int, offset: Int) {
        Utils.showLog("getPokemonlist", "$limit $offset ")

        repository.getAllPokemonNameList(limit, offset).enqueue(
            object : Callback<ModelPokemon> {
                override fun onResponse(
                    call: Call<ModelPokemon>,
                    response: Response<ModelPokemon>
                ) {
                    charactersList.postValue(response.body())
                }

                override fun onFailure(call: Call<ModelPokemon>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

            }
        )
    }


    fun getPokemonNextlist(url: String) {
        Utils.showLog("getPokemonlist next", url)

        repository.getPokemonNextList(url)!!.enqueue(
            object : Callback<ModelPokemon> {
                override fun onResponse(
                    call: Call<ModelPokemon>,
                    response: Response<ModelPokemon>
                ) {
                    charactersList.postValue(response.body())
                }

                override fun onFailure(call: Call<ModelPokemon>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

            }
        )
    }

}

