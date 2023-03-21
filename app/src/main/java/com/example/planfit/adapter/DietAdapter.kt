package com.example.planfit.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planfit.databinding.ItemFoodDietBinding
import com.example.planfit.model.Hint
import com.squareup.picasso.Picasso

class DietAdapter(private val hints: MutableList<Hint> = mutableListOf()) :
    RecyclerView.Adapter<DietAdapter.DietFoodViewHolder>() {

    inner class DietFoodViewHolder(itemBinding: ItemFoodDietBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val title = itemBinding.tvDietFoodTitle
        val description = itemBinding.tvDietFoodDescription
        val image = itemBinding.ivDietFoodFood
        val btDel = itemBinding.btnDeleteDietFood
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DietAdapter.DietFoodViewHolder {
        return DietFoodViewHolder(
            ItemFoodDietBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = hints.size

    override fun onBindViewHolder(holder: DietAdapter.DietFoodViewHolder, position: Int) {
        val hint = hints[position]
        holder.title.text = hint?.food?.label
        holder.description.text = hint?.food?.nutrients.toString()
        if (hint?.food?.image != null)
            Picasso.get().load(Uri.parse(hint.food.image)).into(holder.image)
        else
            holder.image.setImageResource(android.R.color.transparent)

        holder.btDel.setOnClickListener {
            this.hints.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun setHints(items: List<Hint>) {
        this.hints.addAll(items)
        this.notifyDataSetChanged()
    }

    fun getHints():List<Hint>{
        return this.hints
    }
}