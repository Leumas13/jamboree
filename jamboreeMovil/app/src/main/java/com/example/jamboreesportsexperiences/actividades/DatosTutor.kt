package com.example.jamboreesportsexperiences.actividades

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.jamboreesportsexperiences.R
import com.example.jamboreesportsexperiences.odoo.OdooRequestGeneral
import com.example.jamboreesportsexperiences.odoo.OdooResponseSearch
import com.example.jamboreesportsexperiences.odoo.OdooResponseWrite
import com.example.jamboreesportsexperiences.odoo.RequestParams
import com.example.jamboreesportsexperiences.retrofitYservicio.OdooApiService
import com.example.jamboreesportsexperiences.retrofitYservicio.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DatosTutor : AppCompatActivity() {
    //Campos auxiliares que servirán para principalmente reiniciar los datos si el usuario cancela la modificación
    private var nomAux = ""
    private var telAux = ""
    private var codPosAux = ""
    private var ciuAux: Int = 0
    private var provAux: Int = 0
    private var paAux: Int = 0
    private var idTutor:Int=0

    //Verificación auxiliar
    private var codPosExiste = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_tutor)

        //Recuperar mail del tutor e id del administrador
        val emailTutor = intent.getStringExtra("email")
        val idAdmin = intent.getIntExtra("idAdmin", 0)

        //Recuperar todos los campos donde se van a almacenar los datos del tutor
        val nombre = findViewById<EditText>(R.id.nombre)
        val email = findViewById<EditText>(R.id.email)
        val telefono = findViewById<EditText>(R.id.numTel)

        //Campos a utilizar en diferentes funciones privadas
        val codPos = findViewById<EditText>(R.id.codPos)
        val pais = findViewById<EditText>(R.id.pais)
        val provincia = findViewById<EditText>(R.id.provincia)
        val ciudad = findViewById<EditText>(R.id.ciudad)

        //Cargar datos
        if (idAdmin != null && idAdmin != 0) {
            recuperarTutor(idAdmin, emailTutor, nombre, email, telefono,ciudad,provincia,pais)
        } else {
            var alert = AlertDialog.Builder(this)
                .setTitle("Error de conexión")
                .setMessage("No se han podido cargar los datos. " +
                            "Por favor, vuelva a intentarlo una vez se hayan cargado los datos " +
                            "en el calendario.")
                .setPositiveButton("Ok"){ _, _ ->
                    val intent = Intent(this, CalendarioEntrenamientos::class.java)
                    intent.putExtra("email", emailTutor)
                    startActivity(intent)
                }
                .setIcon(R.drawable.error_icono)
                .create()

            alert.show()
        }

        //Recuperar botón para salir al calendario y función que realiza la acción
        val btSal = findViewById<ImageButton>(R.id.btSalir)

        btSal.setOnClickListener {
            val intent = Intent(this, CalendarioEntrenamientos::class.java)
            intent.putExtra("email", emailTutor)
            startActivity(intent)
        }

        //Recuperar botón para salir al principio y función que realiza la acción
        val btMain = findViewById<ImageButton>(R.id.btSalirDat)

        btMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //-----Botones para modificar, cancelar la modificación y guardar los datos-----
        val btMod = findViewById<Button>(R.id.btMod)
        val btCan = findViewById<Button>(R.id.btCanc)
        val btGuardar = findViewById<Button>(R.id.btGuardar)

        //Empezar a modificar
        btMod.setOnClickListener {
            //Botones
            btMod.isEnabled = false
            btMod.visibility = View.INVISIBLE

            btSal.isEnabled = false

            btCan.isEnabled = true
            btCan.visibility = View.VISIBLE

            btGuardar.isEnabled = true
            btGuardar.visibility = View.VISIBLE

            //Campos
            nombre.isEnabled = true
            nombre.setTextColor(resources.getColor(R.color.verdeOscuro, null))

            telefono.isEnabled = true
            telefono.setTextColor(resources.getColor(R.color.verdeOscuro, null))

            codPos.isEnabled = true
            codPos.setTextColor(resources.getColor(R.color.verdeOscuro, null))
        }

        //Cancelar la modificación
        btCan.setOnClickListener {
            //Botones
            btMod.isEnabled = true
            btMod.visibility = View.VISIBLE

            btSal.isEnabled = true

            btCan.isEnabled = false
            btCan.visibility = View.INVISIBLE

            btGuardar.isEnabled = false
            btGuardar.visibility = View.INVISIBLE

            //Reiniciar valores
            nombre.setText(nomAux)
            telefono.setText(telAux)
            codPos.setText(codPosAux)

            //Campos
            nombre.isEnabled = false
            nombre.setTextColor(Color.parseColor("#46555A"))

            telefono.isEnabled = false
            telefono.setTextColor(Color.parseColor("#46555A"))

            codPos.isEnabled = false
            codPos.setTextColor(Color.parseColor("#46555A"))
        }

        //Guardar los Datos
        btGuardar.setOnClickListener {
            if(nombre.text.toString().isNullOrBlank() || telefono.text.toString().isNullOrBlank() || codPos.text.toString().isNullOrBlank()){
                //mensaje que aparece si se deja algún campo vacío
                var alert = AlertDialog.Builder(this)
                    .setTitle("Falta de información")
                    .setMessage("No se puede dejar ningún campo vacío.")
                    .setPositiveButton("Ok",null)
                    .setIcon(R.drawable.informacion)
                    .create()

                alert.show()
            }else{
                //Botones
                btMod.isEnabled = true
                btMod.visibility = View.VISIBLE

                btSal.isEnabled = true

                btCan.isEnabled = false
                btCan.visibility = View.INVISIBLE

                btGuardar.isEnabled = false
                btGuardar.visibility = View.INVISIBLE

                //Campos
                nombre.isEnabled = false
                nombre.setTextColor(Color.parseColor("#46555A"))

                telefono.isEnabled = false
                telefono.setTextColor(Color.parseColor("#46555A"))

                codPos.isEnabled = false
                codPos.setTextColor(Color.parseColor("#46555A"))

                //Modificar
                recuperarCodPost(idAdmin,"name",codPos.text.toString(),true)
                modGeneral(idAdmin, nombre.text.toString(), telefono.text.toString())
                nomAux = nombre.text.toString()
                telAux = telefono.text.toString()
            }
        }
    }

    //Función que recupera los datos del tutor
    private fun recuperarTutor(sessionId: Int, email: String?, nombre: EditText,
                               emailText: EditText, telefono: EditText,
                               ciudad:EditText,provincia:EditText,pais:EditText) {
        //Creamos el servicio para las APIs
        val apiService = RetrofitClient.retrofit.create(OdooApiService::class.java)

        //Se crea la petición que se va a utilizar
        val request = OdooRequestGeneral(
            params = RequestParams(
                service = "object",
                method = "execute_kw",  //metodo general
                args = listOf(
                    "admin",  //Nombre de la base de datos
                    sessionId,                //Id del usuario
                    "clave$1",        //Contraseña del usuario
                    "res.partner",  //Modelo a utilizar
                    "search_read",         //Método a realizar en el modelo
                    listOf(
                        //Filtrar por email
                        listOf(
                            listOf("email", "=", email)
                        )
                    ),
                ),
                kwargs = emptyMap()  //Sin parámetros adicionales
            )
        )

        //Se realiza la petición con el servicio y la propia petición creados anteriormente
        apiService.recuperarInfo(request).enqueue(object :
            Callback<OdooResponseSearch<Map<String, Any>>> {
            override fun onResponse(
                call: Call<OdooResponseSearch<Map<String, Any>>>,
                response: Response<OdooResponseSearch<Map<String, Any>>>
            ) {

                val resultado = response.body()?.result

                //Id
                val id = resultado?.get(0)?.get("id") as Double
                idTutor = id.toInt()

                //Nombre y apellidos
                val nombreCompleto = resultado?.get(0)?.get("complete_name") as String
                nombre.setText(nombreCompleto)

                //Email
                emailText.setText(resultado?.get(0)?.get("email") as String)

                //Teléfono
                val tel = resultado?.get(0)?.get("phone")
                if (tel != false) {
                    telefono.setText(tel as String)
                }

                //Ciudad y Código postal
                val ciu = resultado?.get(0)?.get("city_id")

                if (ciu is ArrayList<*>) {
                    //Id de la tabla con el código postal
                    val ciu2 = ciu as ArrayList<Any>
                    val codZip = resultado?.get(0)?.get("zip_id") as ArrayList<Any>

                    //Código de la ciudad
                    val codCiu = ciu2[0] as Double
                    ciuAux = codCiu.toInt()

                    //Llamada a recuperar el código postal verdadero
                    val zipId = codZip[0] as Double
                    val zipInt = zipId.toInt()
                    recuperarCodPost(sessionId, "id", zipInt,false)
                    ciudad.setText(ciu[1] as String)
                }

                //Provincia
                val prov = resultado?.get(0)?.get("state_id")
                if (prov is ArrayList<*>) {
                    val prov2 = prov as ArrayList<Any>
                    val codP = prov2[0] as Double
                    provAux = codP.toInt()
                    provincia.setText(prov2[1] as String)
                }

                //País
                val paisA = resultado?.get(0)?.get("country_id")
                if (paisA is ArrayList<*>) {
                    val paisA2 = paisA as ArrayList<Any>
                    val codPr = paisA2[0] as Double
                    paAux = codPr.toInt()
                    pais.setText(paisA2[1] as String)
                }

                //Auxiliares
                nomAux = nombre.text.toString()
                telAux = telefono.text.toString()

            }

            override fun onFailure(call: Call<OdooResponseSearch<Map<String, Any>>>, t: Throwable) {
                recuperarTutor(sessionId, email, nombre, emailText,telefono, ciudad, provincia, pais)
            }
        })
    }

    //Función que recupera el código postal a través del Id en la tabla de res.city.zip
    private fun recuperarCodPost(sessionId: Int, campo:String,cod: Any, modificar: Boolean) {

        //creamos el servicio para las APIs
        val apiService = RetrofitClient.retrofit.create(OdooApiService::class.java)

        //se crea la petición que se va a utilizar
        val request = OdooRequestGeneral(
            params = RequestParams(
                service = "object",
                method = "execute_kw",  //Método general
                args = listOf(
                    "admin",  //Nombre de la base de datos
                    sessionId,                //Id del usuario
                    "clave$1",        //Contraseña del usuario
                    "res.city.zip",  //Modelo a utilizar
                    "search_read",         //Método a realizar en el modelo
                    listOf(
                        listOf(
                            listOf(campo, "=", cod)
                        )
                    ),
                ),
                kwargs = emptyMap()  //Sin parámetros adicionales
            )
        )

        //se realiza la petición con el servicio y la propia petición creados anteriormente
        apiService.recuperarInfo(request).enqueue(object :
            Callback<OdooResponseSearch<Map<String, Any>>> {
            override fun onResponse(
                call: Call<OdooResponseSearch<Map<String, Any>>>,
                response: Response<OdooResponseSearch<Map<String, Any>>>
            ) {

                val resultado = response.body()?.result

                val codPos = findViewById<EditText>(R.id.codPos)

                //Se comprueba si se quiere modificar el código postal o solo recuperarlo
                if (modificar) {
                    //Se comprueba si existe el código introducido por "pantalla"
                    if (resultado.isNullOrEmpty()) {
                        var toast = Toast.makeText(this@DatosTutor, "Ese código postal no existe.", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 1075)
                        toast.show()
                        codPos.setText(codPosAux)
                        codPosExiste = false
                    } else {
                        //Recuperar campos
                        val paisC = findViewById<EditText>(R.id.pais)
                        val provinciaC = findViewById<EditText>(R.id.provincia)
                        val ciudadC = findViewById<EditText>(R.id.ciudad)

                        codPosAux = cod.toString()

                        //Recuperar codigo de ciudad, país y provincia
                        val ciudad = resultado?.get(0)?.get("city_id") as ArrayList<Any>
                        val provincia = resultado?.get(0)?.get("state_id") as ArrayList<Any>
                        val pais = resultado?.get(0)?.get("country_id") as ArrayList<Any>
                        val zip = resultado?.get(0)?.get("id") as Double

                        //Separar ciudad
                        val codDoubleCi = ciudad[0] as Double
                        val ciudadFinal = codDoubleCi.toInt()
                        ciudadC.setText(ciudad[1] as String)

                        //Separar provincia
                        val codDoubleProv = provincia[0] as Double
                        val provinciaFinal = codDoubleProv.toInt()
                        provinciaC.setText(provincia[1] as String)

                        //Separar pais
                        val codDoublePais = pais[0] as Double
                        val paisFinal = codDoublePais.toInt()
                        paisC.setText(pais[1] as String)

                        //Zip
                        val zipFinal = zip.toInt()

                        //Método que modifica los datos en la API de odoo
                        modGeografia(sessionId,ciudadFinal,provinciaFinal,paisFinal,zipFinal)
                    }
                } else {
                    //Se recupera el valor y se pinta en la pantalla
                    val codP = resultado?.get(0)?.get("name") as String
                    codPosAux = codP
                    codPos.setText(codP)
                }
            }

            override fun onFailure(call: Call<OdooResponseSearch<Map<String, Any>>>, t: Throwable) {
                recuperarCodPost(sessionId, campo,cod, modificar)
            }
        })
    }

    //Método que modifica únicamente los datos de la provincia, país...
    private fun modGeografia(sessionId: Int,codCiu:Int,codProv:Int,codPais:Int, zip:Int) {
        //Sreamos el servicio para las APIs
        val apiService = RetrofitClient.retrofit.create(OdooApiService::class.java)

        //Se crea la petición que se va a utilizar
        val request = OdooRequestGeneral(
            params = RequestParams(
                service = "object",  //El servicio
                method = "execute_kw",  //El método a llamar
                args = listOf(
                    "admin",  //Nombre de la base de datos
                    sessionId,                //UID del usuario
                    "clave$1",        //Contraseña del usuario
                    "res.partner",  //El modelo que estamos utilizando
                    "write",         //El método en el modelo (write)
                    listOf(
                        listOf(idTutor)  //Filtrar por id
                        ,
                        mapOf( "city_id" to codCiu,"state_id" to codProv, "country_id" to codPais, "zip_id" to zip)
                    ),
                ),
                kwargs = emptyMap()
            )
        )

        //Se realiza la petición con el servicio y la propia petición creados anteriormente
        apiService.modificarDatos(request).enqueue(object : Callback<OdooResponseWrite> {
            override fun onResponse(
                call: Call<OdooResponseWrite>,
                response: Response<OdooResponseWrite>
            ) {
                val response = response.body()?.result as Boolean

                //Se comprueba si al modificar ha ocurrido algún error
                if(!response){
                    var alert = AlertDialog.Builder(this@DatosTutor)
                        .setTitle("Error de ubicación")
                        .setMessage("Ha ocurrido un error al intentar modificar la ubicación. " +
                                    "Por favor, intente modificarla de nuevo.")
                        .setPositiveButton("Ok",null)
                        .setIcon(R.drawable.error_icono)
                        .create()

                    alert.show()
                }
            }

            override fun onFailure(call: Call<OdooResponseWrite>, t: Throwable) {
                println("Error en la modificación")
            }
        })
    }

    //Método que módifica los datos generales del tutor
    private fun modGeneral(sessionId: Int, nombre:String, telefono:String) {
        //Creamos el servicio para las APIs
        val apiService = RetrofitClient.retrofit.create(OdooApiService::class.java)

        //Se crea la petición que se va a utilizar
        val request = OdooRequestGeneral(
            params = RequestParams(
                service = "object",  //El servicio
                method = "execute_kw",  //El método a llamar
                args = listOf(
                    "admin",  //Nombre de la base de datos
                    sessionId,                //UID del usuario
                    "clave$1",        //Contraseña del usuario
                    "res.partner",  //El modelo que estamos utilizando
                    "write",         //El método en el modelo (write)
                    listOf(
                        listOf(idTutor)  //Filtrar por id
                        ,
                        mapOf( "complete_name" to nombre,"name" to nombre,"phone" to telefono)
                    ),
                ),
                kwargs = emptyMap()
            )
        )

        //Se realiza la petición con el servicio y la propia petición creados anteriormente
        apiService.modificarDatos(request).enqueue(object : Callback<OdooResponseWrite> {
            override fun onResponse(
                call: Call<OdooResponseWrite>,
                response: Response<OdooResponseWrite>
            ) {
                val response = response.body()?.result as Boolean

                //Se comprueba si al modificar ha ocurrido algún error
                if(!response){
                    var alert = AlertDialog.Builder(this@DatosTutor)
                        .setTitle("Error de modificación")
                        .setMessage("Ha ocurrido un error al intentar modificar sus datos generales. " +
                                    "Por favor, intente modificarlos de nuevo.")
                        .setPositiveButton("Ok",null)
                        .setIcon(R.drawable.error_icono)
                        .create()

                    alert.show()
                }else{
                    var toast: Toast? = null

                    //Comprobación de que valores se han modificado correctamente
                    if(codPosExiste){
                        toast = Toast.makeText(this@DatosTutor, "Datos modificados correctamente.", Toast.LENGTH_SHORT)
                    }else{
                        toast = Toast.makeText(this@DatosTutor, "Nombre y teléfono modificados correctamente.", Toast.LENGTH_SHORT)
                        codPosExiste = true
                    }

                    toast?.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 1075)
                    toast?.show()
                }
            }

            override fun onFailure(call: Call<OdooResponseWrite>, t: Throwable) {
                println("Error en la modificación")
            }
        })
    }
}