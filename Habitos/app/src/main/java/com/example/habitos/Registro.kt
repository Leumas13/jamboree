package com.example.habitos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

/*
        var ctrlbd = CtrlBaseDatos(this, "prueba", null, 1, "habito1")
        val db = ctrlbd.writableDatabase
        ctrlbd.onCreate(db, "habito2")
        ctrlbd.onCreate(db, "habito3")
        ctrlbd.cargarDatosPrueba1()
*/



        val iniciar = findViewById<Button>(R.id.btnInicio)
        val registro = findViewById<Button>(R.id.btnRegistro)
        val usuario = findViewById<EditText>(R.id.usuario)
        val pass = findViewById<EditText>(R.id.pass)

        registro.setOnClickListener {
            val usuarioTexto = usuario.text.toString()
            val passTexto = pass.text.toString()
            val ctrlbd = CtrlBaseDatos(this, usuarioTexto, null, 1, "habito1")
            val cursor = ctrlbd.cargarDatos()

            if(cursor.moveToFirst()){
                Toast.makeText(
                    this,
                    "El usuario ya existe",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                if (usuario.text.isEmpty() || pass.text.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Rellena los dos datos para dar de alta un nuevo usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "Bienvenid@ ${usuario.text}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NuevoHabito::class.java)
                    intent.putExtra("usuario", usuario.text.toString())
                    intent.putExtra("pass", pass.text.toString())
                    intent.putExtra("registroNuevo", true)
                    startActivity(intent)
                }
            }
        }

        iniciar.setOnClickListener {
            val usuarioTexto = usuario.text.toString()
            val passTexto = pass.text.toString()
            val ctrlbd = CtrlBaseDatos(this, usuarioTexto, null, 1, "habito1")
            val bd = ctrlbd.readableDatabase

            // Validar que ambos campos no estén vacíos

            if (usuarioTexto.isEmpty() || passTexto.isEmpty()) {
                Toast.makeText(this, "Rellene los dos campos", Toast.LENGTH_SHORT).show()
            }

            // Usar parámetros para evitar inyección SQL, como obligo a crear el habito1, busco el usuario en esa tabla, aunque el usuario pueda crear mas tablas asociadas a mas habitos
            val query = "SELECT usuario FROM habito1 WHERE usuario = ? AND pass = ?"
            val cursor = bd.rawQuery(query, arrayOf(usuarioTexto, passTexto))

            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Bienvenid@ $usuarioTexto", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Principal::class.java)
                intent.putExtra("usuario", usuario.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }

            // Cerrar cursor y base de datos
            cursor.close()
            bd.close()
        }


    }

}
