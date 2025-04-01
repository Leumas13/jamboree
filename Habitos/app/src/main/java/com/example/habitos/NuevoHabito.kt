package com.example.habitos

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible

class NuevoHabito : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_habito)

        //info usuario cuando llega desde otro activity
        val bundle: Bundle? = intent.extras
        var info = bundle?.getString("usuario")
        val usuario = info.toString()
        info = bundle?.getString("pass")
        val pass = info.toString()
        var info2 = bundle?.getBoolean("registroNuevo")
        val registroNuevo = info2

        //Bloque menu
        val inicio = findViewById<ImageButton>(R.id.btnHome)
        val salir = findViewById<ImageButton>(R.id.btnSalir)


        inicio.setOnClickListener {
            //el boton funciona si no viene de registro, para obligar a crear un habito antes de saltar al activity
            if(registroNuevo != true) {
                var intent = Intent(this, Principal::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            }
        }

        salir.setOnClickListener {
            var intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }


        //Bloque activity


        //HABITO 1

        //Componentes
        val habito1 = "habito1"
        val nombre1 = findViewById<EditText>(R.id.etNombre1)
        val boton1 = findViewById<Button>(R.id.btnCrear1)
        val grupo1 = findViewById<RadioGroup>(R.id.rbGrupo)
        var imagen1 = ""


        /*busca si existe la base de datos, si existe carga los datos en los campos y cambia
        * el texto del boton, y la función de éste*/
        if (registroNuevo != true) {
            boton1.text = "Modificar"
            val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito1)
            val cursor = ctrlbd.cargarDatos()
            if (cursor.moveToFirst()) {
                nombre1.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")))
                imagen1 = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

                //Recorrer los radioButton para cargar el almacenado en BD
                for (i in 0 until grupo1.childCount) {
                    val radioButton = grupo1.getChildAt(i) as RadioButton
                    if (radioButton.contentDescription.toString() == imagen1) {
                        grupo1.check(radioButton.id) // Selecciona el RadioButton con el ID correspondiente
                    }
                }
            }

        } else {
            boton1.text = "Crear"
        }


        //cuando cambie la seleccion, imagen1 cambia de valor con la descripcion del componente
        grupo1.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb1image0 -> imagen1 =
                    findViewById<RadioButton>(R.id.rb1image0).contentDescription.toString()

                R.id.rb1image1 -> imagen1 =
                    findViewById<RadioButton>(R.id.rb1image1).contentDescription.toString()

                R.id.rb1image2 -> imagen1 =
                    findViewById<RadioButton>(R.id.rb1image2).contentDescription.toString()

                R.id.rb1image3 -> imagen1 =
                    findViewById<RadioButton>(R.id.rb1image3).contentDescription.toString()
            }
        }



        boton1.setOnClickListener {
            if (boton1.text.toString() == "Modificar") {
                actualizarHabito(habito1, nombre1.text.toString(), usuario, imagen1)
            } else {
                //creo las tres tablas, el habito1 peresonalizado y el habito 2 y 3 con valores por defecto
                crearHabito("habito1", nombre1.text.toString(), usuario, pass, imagen1, "1-1-1900")
            }
        }



        //HABITO2

        //Componentes
        val habito2 = "habito2"
        val nombre2 = findViewById<EditText>(R.id.etNombre2)
        val boton2 = findViewById<Button>(R.id.btnCrear2)
        val grupo2 = findViewById<RadioGroup>(R.id.rbGrupo2)
        var imagen2 = ""

        val tvNombre2 = findViewById<TextView>(R.id.tvNombre2)
        val tvHabito2 = findViewById<TextView>(R.id.tvHabito2)
        val tvImagen2 = findViewById<TextView>(R.id.tvImagen2)




        /*busca si existe la base de datos, si existe carga los datos en los campos y cambia
        * el texto del boton, y la función de éste*/
        if (registroNuevo != true) {
            boton2.text = "Modificar"
            val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito2)
            val cursor = ctrlbd.cargarDatos()
            if (cursor.moveToFirst()) {
                nombre2.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")))
                imagen2 = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

                //Recorrer los radioButton para cargar el almacenado en BD
                for (i in 0 until grupo2.childCount) {
                    val radioButton = grupo2.getChildAt(i) as RadioButton
                    if (radioButton.contentDescription.toString() == imagen2) {
                        grupo2.check(radioButton.id) // Selecciona el RadioButton con el ID correspondiente
                    }
                }
            }

        } else {
            //OCULTAR TODO EL HABITO 2, SE VA HA CREAR EL HABITO 1 SOLO
            nombre2.isInvisible = true;
            boton2.isInvisible = true;
            grupo2.isInvisible = true;


            tvNombre2.isInvisible = true;
            tvHabito2.isInvisible = true
            tvImagen2.isInvisible = true
        }


        //cuando cambie la seleccion, imagen2 cambia de valor con la descripcion del componente
        grupo2.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb2image0 -> imagen2 =
                    findViewById<RadioButton>(R.id.rb2image0).contentDescription.toString()

                R.id.rb2image1 -> imagen2 =
                    findViewById<RadioButton>(R.id.rb2image1).contentDescription.toString()

                R.id.rb2image2 -> imagen2 =
                    findViewById<RadioButton>(R.id.rb2image2).contentDescription.toString()

                R.id.rb2image3 -> imagen2 =
                    findViewById<RadioButton>(R.id.rb2image3).contentDescription.toString()
            }
        }



        boton2.setOnClickListener {

            actualizarHabito(habito2, nombre2.text.toString(), usuario, imagen2)
        }




    //HABITO3

    //Componentes
    val habito3 = "habito3"
    val nombre3 = findViewById<EditText>(R.id.etNombre3)
    val boton3 = findViewById<Button>(R.id.btnCrear3)
    val grupo3 = findViewById<RadioGroup>(R.id.rbGrupo3)
    var imagen3 = ""

        val tvNombre3 = findViewById<TextView>(R.id.tvNombre3)
        val tvHabito3 = findViewById<TextView>(R.id.tvHabito3)
        val tvImagen3 = findViewById<TextView>(R.id.tvImagen3)


    /*busca si existe la base de datos, si existe carga los datos en los campos y cambia
    * el texto del boton, y la función de éste*/
    if (registroNuevo != true) {
        boton3.text = "Modificar"
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito3)
        val cursor = ctrlbd.cargarDatos()
        if (cursor.moveToFirst()) {
            nombre3.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")))
            imagen3 = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

            //Recorrer los radioButton para cargar el almacenado en BD
            for (i in 0 until grupo3.childCount) {
                val radioButton = grupo3.getChildAt(i) as RadioButton
                if (radioButton.contentDescription.toString() == imagen3) {
                    grupo3.check(radioButton.id) // Selecciona el RadioButton con el ID correspondiente
                }
            }
        }

    } else {
        //OCULTAR TODO EL HABITO 3

        nombre3.isInvisible = true;
        boton3.isInvisible = true;
        grupo3.isInvisible = true;

        tvNombre3.isInvisible = true;
        tvHabito3.isInvisible = true
        tvImagen3.isInvisible = true
    }


    //cuando cambie la seleccion, imagen3 cambia de valor con la descripcion del componente
    grupo3.setOnCheckedChangeListener { radioGroup, i ->
        when (i) {
            R.id.rb3image0 -> imagen3 =
                findViewById<RadioButton>(R.id.rb3image0).contentDescription.toString()

            R.id.rb3image1 -> imagen3 =
                findViewById<RadioButton>(R.id.rb3image1).contentDescription.toString()

            R.id.rb3image2 -> imagen3 =
                findViewById<RadioButton>(R.id.rb3image2).contentDescription.toString()

            R.id.rb3image3 -> imagen3 =
                findViewById<RadioButton>(R.id.rb3image3).contentDescription.toString()
        }
    }



    boton3.setOnClickListener {
            actualizarHabito(habito3, nombre3.text.toString(), usuario, imagen3)
    }
}






    fun crearHabito(
        habito: String,
        nombre: String,
        usuario: String,
        pass: String,
        imagen: String,
        hoy: String
    ) {
        //no exite la base datos y creo una con el nombre usuario y una tabla habito
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito)
        val bd = ctrlbd.writableDatabase
        val reg = ContentValues()

        ctrlbd.onCreate(bd, "habito2")
        val reg2 = ContentValues()

        ctrlbd.onCreate(bd, "habito3")
        val reg3 = ContentValues()


        if (nombre == "") {
            Toast.makeText(
                this,
                "Escribe el nombre que quieres para tu hábito nuevo",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            //crea el primer registro de la tabla habito1
            reg.put("indice", 0)
            reg.put("fecha", hoy)
            reg.put("nombre", nombre)
            reg.put("imagen", imagen)
            reg.put("usuario", usuario)
            reg.put("pass", pass)
            reg.put("ultimaRacha", 0)
            reg.put("nota", "")
            if (bd.insert(habito, null, reg) != -1L) {

                //si la funcion entra desde el boton crear de habito 1, crea las tablas por defecto de habito 2 y 3
                if(habito == "habito1") {

                    reg2.put("indice", 0)
                    reg2.put("fecha", hoy)
                    reg2.put("nombre", "habito2")
                    reg2.put("imagen", "imagen0")
                    reg2.put("usuario", usuario)
                    reg2.put("pass", pass)
                    reg2.put("ultimaRacha", 0)
                    reg2.put("nota", "")
                    bd.insert("habito2", null, reg2)


                    reg3.put("indice", 0)
                    reg3.put("fecha", hoy)
                    reg3.put("nombre", "habito3")
                    reg3.put("imagen", "imagen0")
                    reg3.put("usuario", usuario)
                    reg3.put("pass", pass)
                    reg3.put("ultimaRacha", 0)
                    reg3.put("nota", "")
                    bd.insert("habito3", null, reg3)
                    Toast.makeText(
                        this,
                        "Hábito ${nombre} creado",
                        Toast.LENGTH_SHORT
                    ).show()
                    var intent = Intent(this, Principal::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Error al crear el hábito $habito", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }
    }

    fun actualizarHabito(
        habito: String,
        nombre: String,
        usuario: String,
        imagen: String) {
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito)
        val db = ctrlbd.writableDatabase
        val queryNombre = "UPDATE $habito SET nombre = ?"
        val queryImagen = "UPDATE $habito SET imagen = ?"


        db.execSQL(queryNombre, arrayOf(nombre))
        db.execSQL(queryImagen, arrayOf(imagen))

        var intent = Intent(this, Principal::class.java)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
}