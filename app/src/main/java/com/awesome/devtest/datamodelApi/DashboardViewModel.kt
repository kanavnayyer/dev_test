package com.awesome.devtest.datamodelApi



import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.awesome.devtest.Api.ApiClient
import com.awesome.devtest.Api.ApiService
import com.awesome.devtest.datamodels.datamodel

class DashboardViewModel : ViewModel() {

    private val _data = MutableLiveData<datamodel>()
    val data: LiveData<datamodel> get() = _data

    private val apiService = ApiClient.retrofit.create(ApiService::class.java)

    @SuppressLint("NullSafeMutableLiveData")
    fun fetchData() {
        viewModelScope.launch {
            try {
                val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI" // Replace with your actual token

                val response = apiService.getDashboardData(token)
                if (response.isSuccessful) {
                    val responseData:datamodel? = response.body()
                    if (responseData != null) {
                        _data.postValue(responseData)
                    } else {
                        Log.e("DashboardViewModel", "Error: Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DashboardViewModel", "Error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Exception: ${e.message}")
            }
        }
    }
}

