package com.android.demo.pokemon.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.demo.pokemon.R
import com.android.demo.pokemon.adapter.PokemonNameAdapter
import com.android.demo.pokemon.databinding.ActivityPokemonNameBinding
import com.android.demo.pokemon.mvvm.MainModelFactory
import com.android.demo.pokemon.mvvm.MainRepository
import com.android.demo.pokemon.mvvm.MainViewModel
import com.android.demo.pokemon.mvvm.RetrofitService
import com.android.demo.pokemon.utils.Utils

class NameListActivity : AppCompatActivity() {
    private var binding: ActivityPokemonNameBinding? = null
    private val limit = 200
    var offset = 0
    var total = 0
    lateinit var nextListUrl: String
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = PokemonNameAdapter()
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_name)

        binding = ActivityPokemonNameBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        viewModel = ViewModelProvider(
            this,
            MainModelFactory(MainRepository(retrofitService))
        ).get(MainViewModel::class.java)

        binding!!.recyclerview.adapter = adapter
        viewModel.charactersList.observe(this, Observer {
            total = it.count!!
            adapter.setCharacterList(it.results)
            nextListUrl = it.next.toString()
            loading = false
        })
        viewModel.errorMessage.observe(this, Observer {
            loading = false
            Utils.showToast(this,getString(R.string.error))
        })
        callCharacters(true, offset)
        binding!!.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && !loading) {
                    // LOAD MORE
                    loading = true
                    if (nextListUrl.isNullOrBlank()) {
                        Utils.showToast(this@NameListActivity,getString(R.string.data_load))
                        //loading = false
                    } else {
                        callCharacters(false, 0)
                    }
                    /*
                    //Scroll - Using offset//
                    val tempoffset = offset.plus(limit)
                     if (tempoffset < total) {
                         callCharacters(false,tempoffset)
                     } else {
                         loading = false
                         Toast.makeText(
                             this@NameListActivity,
                             getString(R.string.data_load),
                             Toast.LENGTH_LONG
                         ).show()
                     }*/
                }
            }
        })
    }

    private fun callCharacters(firstCall: Boolean, calloffset: Int) {
        if (Utils.isOnline(this)) {
            offset = calloffset
            if (firstCall)
                viewModel.getPokemonlist(limit, offset)
            else
                viewModel.getPokemonNextlist(nextListUrl)
        } else {
            Utils.showToast(this,getString(R.string.network_error))
            loading=false
        }
    }
}