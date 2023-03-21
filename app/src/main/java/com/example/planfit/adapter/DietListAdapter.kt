package com.example.planfit.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planfit.DietActivity
import com.example.planfit.databinding.ItemDietBinding
import com.example.planfit.model.Diet

class DietListAdapter(private var diets: MutableList<Diet> = mutableListOf(),
                        private val clickListener: (Diet) -> Unit,
                        private val delClicklListener:(Diet) -> Unit) :
    RecyclerView.Adapter<DietListAdapter.DietListViewHolder>() {

    inner class DietListViewHolder(itemBinding: ItemDietBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        val title = itemBinding.tvDietItemTitle
        val id = itemBinding.tvId
        val del = itemBinding.btnDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietListViewHolder {
        return DietListViewHolder(
            ItemDietBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = diets.size

    override fun onBindViewHolder(holder: DietListViewHolder, position: Int) {
        val diet = diets[position]
        holder.id.text = diet.id.toString()
        holder.title.text = diet.name

        //open new window
        holder.title.setOnClickListener {
            clickListener(diet)
        }

        holder.del.setOnClickListener {
            Log.d("AQUI 01",diet.toString())
            delClicklListener(diet)
            removeDiet(diet)
        }
    }

    /*fun addDiet(diet: Diet) {
        this.diets.add(diet)
        val position = this.diets.indexOf(diet)
        this.notifyItemInserted(position)
    }*/

    fun setDiets(diets:MutableList<Diet>){
        this.diets = diets
        this.notifyDataSetChanged()
    }

    private fun removeDiet(diet: Diet) {
        val position = this.diets.indexOf(diet)
        this.diets.removeAt(position)
        this.notifyItemRemoved(position)
    }

}