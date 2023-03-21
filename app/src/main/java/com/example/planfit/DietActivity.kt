package com.example.planfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planfit.adapter.DietAdapter
import com.example.planfit.databinding.ActivityDietBinding
import com.example.planfit.model.Diet
import com.example.planfit.util.Constants
import com.example.planfit.viewmodel.DietViewModel
import java.math.BigInteger

class DietActivity : AppCompatActivity() {

    private var id: BigInteger? = null
    private val launcherBreakfast = registerActivity(LauncherConstants.BREAKFAST_LAUNCHER)
    private val launcherLunch = registerActivity(LauncherConstants.LUNCH_LAUNCHER)
    private val launcherEveningLunch = registerActivity(LauncherConstants.EVENING_LUNCH_LAUNCHER)
    private val launcherDinner = registerActivity(LauncherConstants.DINNER_LAUNCHER)

    private lateinit var binding: ActivityDietBinding
    private lateinit var viewModel: DietViewModel
    private lateinit var diet: Diet
    private lateinit var breakfastAdapter: DietAdapter
    private lateinit var lunchAdapter: DietAdapter
    private lateinit var eveningLunchAdapter: DietAdapter
    private lateinit var dinnerAdapter: DietAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = DietViewModel(this)

        val intentValue = intent.getStringExtra("id")?.let { BigInteger(it) }
        if (intentValue != 0.toBigInteger())
            id = intentValue

        if (id != null) {
            viewModel.getDietById(id!!).let {
                if (it != null) {
                    diet = it
                }
            }
        } else {
            diet = viewModel.getNewDiet()
        }
    }

    override fun onStart() {
        super.onStart()

        breakfastAdapter = DietAdapter(diet.breakfast)
        lunchAdapter = DietAdapter(diet.lunch)
        eveningLunchAdapter = DietAdapter(diet.eveningLunch)
        dinnerAdapter = DietAdapter(diet.dinner)

        val name = diet.name
        binding.tvDietName.text = Editable.Factory.getInstance().newEditable(name)

        binding.rvBreakFast.layoutManager = LinearLayoutManager(this)
        binding.rvBreakFast.adapter = breakfastAdapter

        binding.rvLunch.layoutManager = LinearLayoutManager(this)
        binding.rvLunch.adapter = lunchAdapter

        binding.rvEveningLunch.layoutManager = LinearLayoutManager(this)
        binding.rvEveningLunch.adapter = eveningLunchAdapter

        binding.rvDinner.layoutManager = LinearLayoutManager(this)
        binding.rvDinner.adapter = dinnerAdapter

        bindingEvents()

    }

    private fun bindingEvents() {
        binding.btAddBreakfastFood.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            launcherBreakfast.launch(intent)
        }

        binding.btAddLunchFood.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            launcherLunch.launch(intent)
        }

        binding.btAddEveningLunchFood.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            launcherEveningLunch.launch(intent)
        }

        binding.btAddDinnerFood.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            launcherDinner.launch(intent)
        }

        binding.btSave.setOnClickListener {
            diet.name = binding.tvDietName.text.toString()
            diet.breakfast = breakfastAdapter.getHints().toMutableList()
            diet.lunch = lunchAdapter.getHints().toMutableList()
            diet.eveningLunch = eveningLunchAdapter.getHints().toMutableList()
            diet.dinner = dinnerAdapter.getHints().toMutableList()

            if(diet.id == 0.toBigInteger())
                viewModel.saveDiet(diet)
            else
                viewModel.updateDiet(diet)

            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.tvDietName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                diet.name = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    private fun registerActivity(launcher: String): ActivityResultLauncher<Intent> {

        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val result = data?.getStringExtra(Constants.FOOD_RESPONSE_FROM_SELECTION)

                result?.let {
                    val list = viewModel.stringToList(it)
                    when (launcher) {
                        LauncherConstants.BREAKFAST_LAUNCHER -> breakfastAdapter.setHints(list)
                        LauncherConstants.LUNCH_LAUNCHER -> lunchAdapter.setHints(list)
                        LauncherConstants.EVENING_LUNCH_LAUNCHER -> eveningLunchAdapter.setHints(
                            list
                        )
                        LauncherConstants.DINNER_LAUNCHER -> dinnerAdapter.setHints(list)
                    }

                }

            }
        }
    }

}

class LauncherConstants {
    companion object {
        const val BREAKFAST_LAUNCHER = "breakfast"
        const val LUNCH_LAUNCHER = "lunch"
        const val EVENING_LUNCH_LAUNCHER = "eveninglunch"
        const val DINNER_LAUNCHER = "dinner"
    }
}