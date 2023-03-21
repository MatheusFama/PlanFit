package com.example.planfit.model

data class Measure(
    val label: String,
    val qualified: List<List<Qualified>>,
    val uri: String
)