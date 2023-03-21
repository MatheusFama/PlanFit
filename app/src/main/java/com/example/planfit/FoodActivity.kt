package com.example.planfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planfit.adapter.FoodAdapter
import com.example.planfit.databinding.ActivityFoodBinding
import com.example.planfit.util.Constants
import com.example.planfit.viewmodel.MainViewModel

class FoodActivity : AppCompatActivity() {

    lateinit var binding : ActivityFoodBinding
    private val viewModel: MainViewModel = MainViewModel()
    private val adapter = FoodAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.rvFood.layoutManager = LinearLayoutManager(this)
        binding.rvFood.adapter = adapter

        bindingViewModelObservers()
        bindingEvents()

        viewModel.getHints(null)

    }

    private fun bindingEvents() {

        binding.svFood.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getHints(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        binding.btAdd.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.FOOD_RESPONSE_FROM_SELECTION, viewModel.listToString(adapter.getSelectedHints()))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun bindingViewModelObservers() {
        viewModel.hints.observe((this)) {
            adapter.setHints(it.toList())
        }

        viewModel.loading.observe((this)) {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe((this)) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}