package com.example.habitos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class Nota : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        //info usuario cuando llega desde otro activity
        val bundle: Bundle? = intent.extras
        var info = bundle?.getString("usuario")
        val usuario = info.toString()
        info = bundle?.getString("tabla")
        val tabla = info.toString()


        //Bloque menu
        val inicio = findViewById<ImageButton>(R.id.btnHome)
        val habitos = findViewById<ImageButton>(R.id.btnHabitos)
        val salir = findViewById<ImageButton>(R.id.btnSalir)

        inicio.setOnClickListener {
            var intent = Intent(this, Principal::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        habitos.setOnClickListener {
            var intent = Intent(this, NuevoHabito::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        salir.setOnClickListener {
            var intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        //Bloque activity

        //Componentes del habito1
        val btnNota = findViewById<Button>(R.id.btnNota)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val fechaTitulo = findViewById<TextView>(R.id.fechaNota)
        val nombre1 = findViewById<TextView>(R.id.tituloHabito1)
        val img1 = findViewById<ImageView>(R.id.imgRachaOk1)
        val textoNota = findViewById<TextView>(R.id.textoNota)

        //carga datos iniciales de base de datos de habito1
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, tabla)
        val cursor = ctrlbd.cargarDatos()
        cursor.moveToFirst()
        nombre1.text = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
        textoNota.text = cursor.getString(cursor.getColumnIndexOrThrow("nota"))
        val fechaDato = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
        val imagen1 = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

        //se carga desde recursos la imagen segun el nombre almacenado en base de datos
        when (imagen1) {
            "imagen0" -> img1.setImageResource(R.drawable.imagen0)
            "imagen1" -> img1.setImageResource(R.drawable.imagen1)
            "imagen2" -> img1.setImageResource(R.drawable.imagen2)
            "imagen3" -> img1.setImageResource(R.drawable.imagen3)
        }

        //se recupera la fecha
        val hoy = LocalDateTime.now()
        val dia = hoy.dayOfMonth
        var mes = hoy.monthValue
        val year = hoy.year

        fechaTitulo.text = "$dia-$mes-$year"

        btnNota.setOnClickListener{
            val texto = textoNota.text.toString()
                val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, tabla)

                ctrlbd.insertarNota(texto, fechaDato)

                //volver a principal
                val intent = Intent(this, Principal::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
        }

        btnCancelar.setOnClickListener{
            //volver a principal
            val intent = Intent(this, Principal::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }
}
