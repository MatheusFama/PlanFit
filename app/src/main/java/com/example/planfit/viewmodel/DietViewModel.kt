package com.example.planfit.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.planfit.model.Diet
import com.example.planfit.model.Hint
import com.example.planfit.model.Profile
import com.example.planfit.network.ProfileInstance
import com.google.gson.Gson
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*

class DietViewModel(val context: Context) : ViewModel() {

    fun getDiets(): MutableList<Diet> {
        val profile = ProfileInstance.getProfile(context)
        return profile.diets
    }

    fun saveDiet(diet: Diet) {
        val formatter = SimpleDateFormat("yyyyMMddhhmmss")
        val date = Date()
        diet.id = BigInteger(formatter.format(date))
        val profile = ProfileInstance.getProfile(context)
        profile.diets.add(diet)

        ProfileInstance.saveProfile(context, profile)
    }

    fun removeDiet(id: BigInteger) {
        val profile = ProfileInstance.getProfile(context)
        val diets = profile.diets.filter {it.id == id }
       Log.d("DIET IN PROFILE",diets.toString())
        profile.diets.removeIf {it.id == id }
        ProfileInstance.saveProfile(context, profile)
    }

    fun getDietById(id: BigInteger): Diet? {
        val profile = ProfileInstance.getProfile(context)
        return profile.diets.firstOrNull { d -> d.id == id }
    }

    fun getNewDiet(): Diet {

        return Diet(
            id = 0.toBigInteger(),
            name = "",
            breakfast = mutableListOf(),
            lunch = mutableListOf(),
            eveningLunch = mutableListOf(),
            dinner = mutableListOf()
        )
    }

    fun updateDiet(diet:Diet){
        val profile = ProfileInstance.getProfile(context)
        profile.diets.firstOrNull { it.id == diet.id }?.let {
            it.name = diet.name
            it.breakfast = diet.breakfast
            it.lunch = diet.lunch
            it.eveningLunch = diet.eveningLunch
            it.dinner = diet.dinner
        }

        ProfileInstance.saveProfile(context, profile)
    }

    fun stringToList(data: String): MutableList<Hint>{
        val gson = Gson()
        val result = gson.fromJson(data, Array<Hint>::class.java)
        return result.toMutableList()
    }

    fun listTostring(data: List<Hint>): String {
        val gson = Gson()
        return gson.toJson(data)
    }
}