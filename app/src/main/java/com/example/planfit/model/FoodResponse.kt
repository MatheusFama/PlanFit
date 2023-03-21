package com.example.planfit.model

data class FoodResponse(
    val _links: Links,
    val hints: MutableList<Hint>,
    val parsed: MutableList<Any>,
    val text: String?
): java.io.Serializable