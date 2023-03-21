package com.example.planfit.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planfit.databinding.ItemFoodBinding
import com.example.planfit.model.Hint
import com.squareup.picasso.Picasso

class FoodAdapter(private val hints: MutableList<Hint> = mutableListOf()) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemBinding: ItemFoodBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val title = itemBinding.tvTitle
        val image = itemBinding.ivFood
        val description = itemBinding.tvDescription
        val checkbox = itemBinding.cbFood
        val foodId = itemBinding.foodId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = hints.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val hint = hints[position]
        holder.title.text = hint?.food?.label
        holder.description.text = hint?.food?.nutrients.toString()
        holder.foodId.text = hint.food.foodId
        holder.checkbox.isChecked = hint.checked

        if (hint?.food?.image != null)
            Picasso.get().load(Uri.parse(hint.food.image)).into(holder.image)
        else
            holder.image.setImageResource(android.R.color.transparent)

        //Event
        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.setOnClickListener {
            hint.checked = !hint.checked
        }
    }

    private fun removeAllUnchecked(){
        this.hints.retainAll { i-> i.checked }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHints(items: List<Hint>) {
        removeAllUnchecked()
        this.hints.addAll(items)
        this.notifyDataSetChanged()
    }

    fun getSelectedHints(): List<Hint> {
        return hints.filter { i -> i.checked }
    }

}