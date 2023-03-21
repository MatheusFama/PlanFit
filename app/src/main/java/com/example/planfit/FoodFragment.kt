package com.example.planfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planfit.adapter.FoodAdapter
import com.example.planfit.databinding.FragmentFoodBinding
import com.example.planfit.viewmodel.MainViewModel

class FoodFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentFoodBinding
    private val adapter = FoodAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFood.layoutManager = LinearLayoutManager(context)
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
    }

    private fun bindingViewModelObservers() {
        viewModel.hints.observe((this.activity as MainActivity)) {
            adapter.setHints(it.toList())
        }

        viewModel.loading.observe((this.activity as MainActivity)) {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe((this.activity as MainActivity)) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

