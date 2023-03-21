package com.example.planfit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planfit.model.Hint
import com.example.planfit.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    val hints = MutableLiveData<List<Hint>>()
    val loading = MutableLiveData<Boolean>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val errorMessage = MutableLiveData<String>()

    fun getHints(ingredient: String?) {
        loading.postValue(true)
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = RetrofitInstance.api.getFoods(Ingredient = ingredient)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    hints.postValue( response.body()?.hints)
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    fun listToString(hints: List<Hint>):String{
        val gson = Gson()
        return gson.toJson(hints)
    }

    override fun onCleared() {
        super.onCleared()
    }
}