package com.example.jamboreesportsexperiences.login

import android.util.Log
import com.example.jamboreesportsexperiences.odoo.OdooRequestLogin
import com.example.jamboreesportsexperiences.odoo.OdooResponseLogin
import com.example.jamboreesportsexperiences.retrofitYservicio.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeticionLoginAPI {
    //Función callback debido a que se hace un metodo asíncrono(enqueue)
    fun login(
        username: String,
        password: String,
        //"Funcion" que recupera el valor devuelto por odoo
        exito: (Int?) -> Unit
    ) {
        //variable que especifica que tipo de llamada se va a realizar
        val request = OdooRequestLogin.login("admin", username, password)

        //llamada a la API de odoo a través de Retrofit de Android Studio
        RetrofitClient.odooApi.login(request).enqueue(object : Callback<OdooResponseLogin> {
            override fun onResponse(call: Call<OdooResponseLogin>, response: Response<OdooResponseLogin>) {
                //comprobación de errores
                if (response.isSuccessful) {
                    val uidRes = response.body()?.result as? Double

                    if(uidRes!=null){
                        val uid = uidRes?.toInt()
                        exito(uid)
                    }else{
                        exito(null)
                    }

                } else {
                    Log.e("ODOO_API", "Error en login: ${response.errorBody()}")
                    exito(null)
                }
            }

            override fun onFailure(call: Call<OdooResponseLogin>, t: Throwable) {
                Log.e("ODOO_API", "Fallo en la solicitud: ${t.message}")
                exito(null)
            }
        })
    }
}