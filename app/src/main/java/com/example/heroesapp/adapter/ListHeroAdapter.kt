package com.example.heroesapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.heroesapp.model.Hero
import com.example.heroesapp.R
import com.example.heroesapp.databinding.ItemRowHeroBinding
import com.example.heroesapp.ui.DetailActivity

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    class ListViewHolder(var binidng: ItemRowHeroBinding) : RecyclerView.ViewHolder(binidng.root)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listHero.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val hero = listHero[position]
        Log.d("ListHeroAdapter", "Hero photo ID: ${hero.photo}")
        Glide.with(holder.itemView.context).load(hero.photo).into(holder.binidng.imgItemPhoto)
        holder.binidng.tvItemName.text = hero.name
        holder.binidng.tvItemDescription.text = hero.description

        holder.itemView.setOnClickListener {
            var goDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            goDetail.putExtra(DetailActivity.EXTRA_HERO, hero)
            holder.itemView.context.startActivity(goDetail)
        }
    }

}