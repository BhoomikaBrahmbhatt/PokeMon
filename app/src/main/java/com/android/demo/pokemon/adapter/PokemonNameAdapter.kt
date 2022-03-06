package com.android.demo.pokemon.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.demo.pokemon.R
import com.android.demo.pokemon.activity.DisplayDetailActivity
import com.android.demo.pokemon.databinding.AdapterPokemonNameBinding
import com.android.demo.pokemon.mvvm.Results
import com.android.demo.pokemon.utils.Utils

class PokemonNameAdapter : RecyclerView.Adapter<MainViewHolder>() {
    var characternameList = mutableListOf<Results>()
    lateinit var mcontext: Context

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacterList(characternameList: List<Results>) {
        this.characternameList.addAll(characternameList.toMutableList())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        mcontext = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPokemonNameBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val character = characternameList[position]
        val name =Utils.getStringCapitalise(character.name!!)
        holder.binding.name.text =name // "$position $name"
        holder.binding.nameLayout.setOnClickListener {
            if (Utils.isOnline(mcontext)) {
                val intent = Intent(mcontext, DisplayDetailActivity::class.java).apply {
                    putExtra(Utils.POKEMON_URL, character.url)
                }
                mcontext.startActivity(intent)
            }else{
                Utils.showToast(mcontext,mcontext.getString(R.string.network_error))
            }
        }
    }

    override fun getItemCount(): Int {
        return characternameList.size
    }
}

class MainViewHolder(val binding: AdapterPokemonNameBinding) :
    RecyclerView.ViewHolder(binding.root) {
}