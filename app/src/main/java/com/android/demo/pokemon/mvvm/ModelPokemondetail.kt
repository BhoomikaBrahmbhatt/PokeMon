package com.android.demo.pokemon.mvvm

import com.google.gson.annotations.SerializedName


data class ModelPokemondetail(

    @SerializedName("height"                   ) var height                 : Int?                   = null,
    @SerializedName("id"                       ) var id                     : Int?                   = null,
    @SerializedName("name"                     ) var name                   : String?                = null,
    @SerializedName("sprites"                  ) var sprites                : Sprites?               = Sprites(),
    @SerializedName("types"                    ) var types                  : ArrayList<Types>       = arrayListOf(),
    @SerializedName("weight"                   ) var weight                 : Int?                   = null

)
data class Sprites (
    @SerializedName("back_default"       ) var backDefault      : String?   = null,
    @SerializedName("front_default"      ) var frontDefault     : String?   = null,

)


data class Types (

    @SerializedName("slot" ) var slot : Int?  = null,
    @SerializedName("type" ) var type : Type? = Type()

)
data class Type (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("url"  ) var url  : String? = null

)
