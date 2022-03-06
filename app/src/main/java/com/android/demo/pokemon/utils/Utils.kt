package com.android.demo.pokemon.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast

object Utils {

    const val POKEMON_URL: String = "PokemonUrl"
    infix fun <T> Boolean.then(value: T?) = TernaryExpression(this, value)

    class TernaryExpression<out T>(val flag: Boolean, val truly: T?) {
        infix fun <T> or(falsy: T?) = if (flag) truly else falsy
    }

    fun getStringCapitalise(string: String): String {
        return string?.replaceFirstChar { it.uppercase() }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
    fun showLog(tag : String, message:String){
        Log.d(tag,message)
    }
    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}