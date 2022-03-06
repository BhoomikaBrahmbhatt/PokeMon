package com.android.demo.pokemon.activity

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.android.demo.pokemon.R
import com.android.demo.pokemon.databinding.ActivityPokemonDetailBinding
import com.android.demo.pokemon.mvvm.*
import com.android.demo.pokemon.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class DisplayDetailActivity : AppCompatActivity() {
    private var binding: ActivityPokemonDetailBinding? = null
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var callUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        supportActionBar?.apply {
            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        callUrl = intent.extras!!.get(Utils.POKEMON_URL) as String

        viewModel = ViewModelProvider(
            this,
            MainModelFactory(MainRepository(retrofitService))
        ).get(MainViewModel::class.java)

        viewModel.characterDetail.observe(this, Observer { modelPokemondetail ->
            val name = Utils.getStringCapitalise(modelPokemondetail.name!!)

            binding!!.textName.text = name
            val height = modelPokemondetail.height?.times(10).toString() // as its in decimetre
            val weight = modelPokemondetail.weight?.div(10).toString() // as its in hectogram
            binding!!.textHeight.text = height.plus(getString(R.string.cm))
            binding!!.textWeight.text = weight.plus(getString(R.string.kg))
            val list: List<Types> = modelPokemondetail.types
            list.forEachIndexed { index, types ->
                setCustomTextViewforTypes(index, types)
            }
            val pokemonImage = modelPokemondetail.sprites!!.frontDefault
            Glide.with(this).load(pokemonImage).addListener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        p0: GlideException?,
                        p1: Any?,
                        p2: Target<Drawable>?,
                        p3: Boolean
                    ): Boolean {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResourceReady(
                        p0: Drawable?,
                        p1: Any?,
                        p2: Target<Drawable>?,
                        p3: DataSource?,
                        p4: Boolean
                    ): Boolean {
                        try {
                            val bm = (p0 as BitmapDrawable).bitmap
                            val palette = Palette.Builder(bm).generate()
                            val swatch = palette.vibrantSwatch
                            binding!!.backgroundLinear.setBackgroundColor(swatch!!.rgb)
                            supportActionBar?.setBackgroundDrawable(ColorDrawable(palette.mutedSwatch!!.rgb))
                        } catch (ex: Exception) {
                            ex.toString()
                        }
                        return false
                    }
                }

            ).into(
                binding!!.imagePokemon
            )
        })
        viewModel.errorMessageDetail.observe(this, Observer {
            Utils.showToast(this,getString(R.string.error))
        })
        if (Utils.isOnline(this))
            viewModel.getPokemondetail(callUrl)
        else
            Utils.showToast(this,getString(R.string.network_error))
    }

    private fun setCustomTextViewforTypes(index: Int, types: Types) {
        try {
            val textViewTypes = TextView(this)

            textViewTypes.setTextColor(ContextCompat.getColor(this, R.color.black))
            textViewTypes.textSize = 14f
            textViewTypes.background = getDrawable(R.drawable.pockemon_type_background)
            textViewTypes.setPadding(14, 8, 14, 8);
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (index > 0)
                layoutParams.setMargins(6, 0, 0, 0)
            else
                layoutParams.setMargins(0, 0, 0, 0)
            textViewTypes.text = Utils.getStringCapitalise(types.type!!.name!!)
            binding!!.linearTypes.addView(textViewTypes, layoutParams)
        } catch (ex: Exception) {
            ex.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}