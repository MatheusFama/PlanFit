package com.example.planfit.network

import android.content.Context
import com.example.planfit.model.Profile
import com.google.gson.Gson

class ProfileInstance {
    companion object {
        fun getProfile(context: Context): Profile {
            val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val profileData = data.getString("profile", null)

            val profile: Profile? = profileData.let {
                if (it != null) {
                    val gson = Gson()
                    gson.fromJson(it, Profile::class.java)

                } else {
                    null
                }
            }
            return profile ?: Profile(mutableListOf())

        }

        fun saveProfile(context: Context, profile: Profile) {
            val gson = Gson()
            val profileData = gson.toJson(profile)

            val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString("profile", profileData)
            editor.commit()

        }
    }


}