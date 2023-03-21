package com.example.planfit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planfit.adapter.DietListAdapter
import com.example.planfit.databinding.FragmentDietListBinding
import com.example.planfit.viewmodel.DietViewModel

class DietListFragment : Fragment() {

    private lateinit var viewModel: DietViewModel
    private lateinit var binding: FragmentDietListBinding
    private lateinit var adapter: DietListAdapter
    private val launcher = registerActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            this,
            DietViewModelFactory(this.requireContext())
        )[DietViewModel::class.java]
        binding = FragmentDietListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindingEvents()
    }

    override fun onStart() {
        super.onStart()
        adapter = DietListAdapter(viewModel.getDiets(), {
            val intent = Intent(this.context, DietActivity::class.java)
            intent.putExtra("id", it.id.toString())
            launcher.launch(intent)
        }, {
            viewModel.removeDiet(it.id)
        })

        binding.rvDiet.layoutManager = LinearLayoutManager(context)
        binding.rvDiet.adapter = adapter
    }

    private fun bindingEvents() {
        binding.btNewDiet.setOnClickListener {
            val intent = Intent(this.context, DietActivity::class.java)
            intent.putExtra("id", "0")
            launcher.launch(intent)
        }
    }

    private fun registerActivity(): ActivityResultLauncher<Intent> {

        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                adapter.setDiets(viewModel.getDiets())
            }
        }
    }


}


class DietViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietViewModel::class.java)) {
            return DietViewModel(context) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
