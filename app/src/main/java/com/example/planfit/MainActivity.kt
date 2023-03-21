package com.example.planfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.planfit.databinding.ActivityMainBinding
import com.example.planfit.viewmodel.DietViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingEvents()
    }

    private fun bindingEvents(){

        //Navigation Menu
        binding.nvMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iTraining -> replaceFragment(TrainingFragment())
                //R.id.iDiet -> replaceFragment(FoodFragment())
                R.id.iDiet -> replaceFragment(DietListFragment())

            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}


