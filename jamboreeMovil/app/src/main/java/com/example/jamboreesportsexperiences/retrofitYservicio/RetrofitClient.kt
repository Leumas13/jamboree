package com.example.jamboreesportsexperiences.retrofitYservicio

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //creación del objeto retrofit de Android que es necesario para poder conectarse con las APIs
     val retrofit = Retrofit.Builder()
        .baseUrl("http://52.47.154.185:8069/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //variable que refleja el servicio de Apis de odoo
    val odooApi: OdooApiService = retrofit.create(OdooApiService::class.java)
}