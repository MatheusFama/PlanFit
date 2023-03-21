package com.example.planfit.model

import java.math.BigInteger

class Profile(
    var diets: MutableList<Diet>
){
    fun addDiet(diet:Diet){
        this.diets.add(diet)
    }

    fun removeDiet(position:Int){
        this.diets.removeAt(position)
    }
}

class Diet(
    var id : BigInteger,
    var name: String,
    var breakfast: MutableList<Hint>,
    var lunch: MutableList<Hint>,
    var eveningLunch: MutableList<Hint>,
    var dinner: MutableList<Hint>
){
    override fun toString(): String {
        return "$id"
    }
}