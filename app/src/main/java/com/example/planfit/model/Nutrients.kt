package com.example.planfit.model

data class Nutrients(
    val CHOCDF: Double?,
    val ENERC_KCAL: Double?,
    val FAT: Double?,
    val FIBTG: Double?,
    val PROCNT: Double?
){
    override fun toString() : String{
        return "CHOCDF:${CHOCDF?:0}\nENERC_KCAL:${ENERC_KCAL?:0}\nFAT:${FAT?:0}\nFIBTF:${FIBTG?:0}\nPROCNT:${PROCNT?:0}"
    }
}