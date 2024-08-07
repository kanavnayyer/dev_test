package com.awesome.devtest.Api

import com.awesome.devtest.datamodels.Datam
import com.awesome.devtest.datamodels.datamodel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header




interface ApiService {

    @GET("api/v1/dashboardNew")
    suspend fun getDashboardData(
        @Header("Authorization") token: String
    ): Response<datamodel>
}
