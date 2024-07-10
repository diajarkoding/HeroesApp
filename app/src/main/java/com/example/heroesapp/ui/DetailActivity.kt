package com.example.heroesapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.heroesapp.R
import com.example.heroesapp.databinding.ActivityDetailBinding
import com.example.heroesapp.model.Hero

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_HERO = "extra_hero"
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataHero = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra<Hero>(EXTRA_HERO, Hero::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Hero>(EXTRA_HERO)
        }
        if (dataHero != null) {
            binding.nameDetailHero.text = dataHero.name
            Glide.with(this@DetailActivity).load(dataHero.photo).into(binding.imgDetailHero)
            binding.tvDetailDescription.text = dataHero.description
        }

        binding.icBackDetail.setOnClickListener{
            finish()
        }

        binding.fabShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, ("${dataHero?.name} merupakan pahlawan nasional Indonesia. \nBerikut deskripsi beliau : \n${dataHero?.description}"))
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Send To"))
        }
    }
}