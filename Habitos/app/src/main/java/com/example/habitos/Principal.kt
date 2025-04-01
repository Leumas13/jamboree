package com.example.habitos

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class Principal : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        //info usuario cuando llega desde otro activity
        val bundle: Bundle? = intent.extras
        val info = bundle?.getString("usuario")
        val usuario = info.toString()



        //Bloque menu
        val habitos = findViewById<ImageButton>(R.id.btnHabitos)
        val salir = findViewById<ImageButton>(R.id.btnSalir)

        habitos.setOnClickListener {
            val intent = Intent(this, NuevoHabito::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        salir.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        //Bloque activity

        //HABITO 1

        //Componentes del habito1
        val btnRacha1 = findViewById<Button>(R.id.btnAcumular1)
        val btnResume1 = findViewById<Button>(R.id.btnResumen1)
        val racha1 = findViewById<TextView>(R.id.contadorRacha1)
        val rachaMax1 = findViewById<TextView>(R.id.rachaMax1)
        val nombre1 = findViewById<TextView>(R.id.tituloHabito1)
        val img1 = findViewById<ImageView>(R.id.imgRachaOk1)
        val btnNota1 = findViewById<Button>(R.id.btnNota)

        //carga datos iniciales de base de datos de habito1
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, "habito1")
        val cursor = ctrlbd.cargarDatos()
        cursor.moveToFirst()
        nombre1.text = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
        val imagen1 = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

        //se carga desde recursos la imagen segun el nombre almacenado en base de datos
        when (imagen1) {
            "imagen0" -> img1.setImageResource(R.drawable.imagen0)
            "imagen1" -> img1.setImageResource(R.drawable.imagen1)
            "imagen2" -> img1.setImageResource(R.drawable.imagen2)
            "imagen3" -> img1.setImageResource(R.drawable.imagen3)
        }

        //se recupera la ultima racha acumulada
        val ultimaRacha = ctrlbd.cargarRacha()
        ultimaRacha.moveToFirst()
        val ultimaRachaTexto = ultimaRacha.getString(ultimaRacha.getColumnIndexOrThrow("ultimaRacha"))
        if(comprobarRacha("habito1", usuario) || habilitarNota("habito1", usuario)){
            racha1.text = "$ultimaRachaTexto días"
        }else{
            racha1.text = "0 días"

//            // Reproducir sonido de éxito
//            val mediaPlayer = MediaPlayer.create(this, R.raw.derrota)
//            mediaPlayer.start()
//
//            // Liberar recursos al terminar
//            mediaPlayer.setOnCompletionListener {
//                mediaPlayer.release()
//            }
        }


        //se recupera la racha maxima desde que se creo el habito
        val rachaMaxima = ctrlbd.cargarMaxima()
        rachaMaxima.moveToFirst()

        val maxTexto = rachaMaxima.getString(rachaMaxima.getColumnIndexOrThrow("MAX(ultimaRacha)"))
        rachaMax1.text = "$maxTexto días"

        //se activa el boton Nota
        if(habilitarNota("habito1",usuario)){
            btnNota1.isEnabled = true
        }else{
            btnNota1.isEnabled = false
        }

        //boton OK para sumar un dia a la racha y registrarlo en base de datos
        btnRacha1.setOnClickListener {
            val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, "habito1")
            var bd = ctrlbd.writableDatabase
            val cursor = ctrlbd.cargarDatos()
            cursor.moveToFirst()

            val rachaInt = cursor.getString(cursor.getColumnIndexOrThrow("ultimaRacha")).toInt()
            var racha = 0;
            var ultimaFecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))


            val hoy = LocalDateTime.now()
            val dia = hoy.dayOfMonth
            var mes = hoy.monthValue - 1
            if (mes == -1) {
                mes = 11
            }
            val year = hoy.year

            val fecha = "$dia-$mes-$year"

            if (ultimaFecha.equals(fecha)) {
                Toast.makeText(
                    this,
                    "Tu racha esta al dia!!! Mañana más!!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if (comprobarRacha("habito1", usuario)) {
                    racha = rachaInt + 1;
                } else {
                    racha = 1
                }
                Toast.makeText(
                    this,
                    "2 fecha $ultimaFecha, racha $racha",
                    Toast.LENGTH_SHORT
                ).show()

                ctrlbd.insertarRegistro(fecha, racha)


                val rachaTexto = racha.toString()
                racha1.text = "$rachaTexto días"

                val rachaMaxima = ctrlbd.cargarMaxima()
                rachaMaxima.moveToFirst()

                val maxTexto = rachaMaxima.getString(rachaMaxima.getColumnIndexOrThrow("MAX(ultimaRacha)"))
                rachaMax1.text = "$maxTexto días"

                //se habilita el boton Nota
                btnNota1.isEnabled = true

                // Reproducir sonido de éxito
                val mediaPlayer = MediaPlayer.create(this, R.raw.victoria)
                mediaPlayer.start()

                // Liberar recursos al terminar
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                }
            }

        }

        //boton Resumen para consultar un calendario con los dias marcados
        btnResume1.setOnClickListener{
            var intent = Intent(this, CalendarioRacha::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("tabla", "habito1")
            startActivity(intent)
        }

        btnNota1.setOnClickListener{
            var intent = Intent(this, Nota::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("tabla", "habito1")
            startActivity(intent)
        }


        //HABITO 2

        //Componentes del habito1
        val btnRacha2 = findViewById<Button>(R.id.btnAcumular2)
        val btnResume2 = findViewById<Button>(R.id.btnResumen2)
        val racha2 = findViewById<TextView>(R.id.contadorRacha2)
        val rachaMax2 = findViewById<TextView>(R.id.rachaMax2)
        val nombre2 = findViewById<TextView>(R.id.tituloHabito2)
        val img2 = findViewById<ImageView>(R.id.imgRachaOk2)
        val btnNota2 = findViewById<Button>(R.id.btnNota2)

        //carga datos iniciales de base de datos de habito1
        val ctrlbd2 = CtrlBaseDatos(this, usuario, null, 1, "habito2")
        val cursor2 = ctrlbd2.cargarDatos()
        cursor2.moveToFirst()
        nombre2.text = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"))
        val imagen2 = cursor2.getString(cursor2.getColumnIndexOrThrow("imagen"))

        //se carga desde recursos la imagen segun el nombre almacenado en base de datos
        when (imagen2) {
            "imagen0" -> img2.setImageResource(R.drawable.imagen0)
            "imagen1" -> img2.setImageResource(R.drawable.imagen1)
            "imagen2" -> img2.setImageResource(R.drawable.imagen2)
            "imagen3" -> img2.setImageResource(R.drawable.imagen3)
        }

        //se recupera la ultima racha acumulada
        val ultimaRacha2 = ctrlbd2.cargarRacha()
        ultimaRacha2.moveToFirst()
        val ultimaRachaTexto2 = ultimaRacha2.getString(ultimaRacha2.getColumnIndexOrThrow("ultimaRacha"))
        if(comprobarRacha("habito2", usuario) || habilitarNota("habito2", usuario)){
            racha2.text = "$ultimaRachaTexto2 días"
        }else{
            racha2.text = "0 días"

//            // Reproducir sonido de éxito
//            val mediaPlayer = MediaPlayer.create(this, R.raw.derrota)
//            mediaPlayer.start()
//
//            // Liberar recursos al terminar
//            mediaPlayer.setOnCompletionListener {
//                mediaPlayer.release()
//            }
        }


        //se recupera la racha maxima desde que se creo el habito
        val rachaMaxima2 = ctrlbd2.cargarMaxima()
        rachaMaxima2.moveToFirst()

        val maxTexto2 = rachaMaxima2.getString(rachaMaxima2.getColumnIndexOrThrow("MAX(ultimaRacha)"))
        rachaMax2.text = "$maxTexto2 días"

        //se activa el boton Nota
        if(habilitarNota("habito2",usuario)){
            btnNota2.isEnabled = true
        }else{
            btnNota2.isEnabled = false
        }

        //boton OK para sumar un dia a la racha y registrarlo en base de datos
        btnRacha2.setOnClickListener {
            val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, "habito2")
            var bd = ctrlbd.writableDatabase
            val cursor = ctrlbd.cargarDatos()
            cursor.moveToFirst()

            val rachaInt = cursor.getString(cursor.getColumnIndexOrThrow("ultimaRacha")).toInt()
            var racha = 0;
            var ultimaFecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))


            val hoy = LocalDateTime.now()
            val dia = hoy.dayOfMonth
            var mes = hoy.monthValue - 1
            if (mes == -1) {
                mes = 11
            }
            val year = hoy.year

            val fecha = "$dia-$mes-$year"

            if (ultimaFecha.equals(fecha)) {
                Toast.makeText(
                    this,
                    "Tu racha esta al dia!!! Mañana más!!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if (comprobarRacha("habito2", usuario)) {
                    racha = rachaInt + 1;
                } else {
                    racha = 1
                }
                Toast.makeText(
                    this,
                    "2 fecha $ultimaFecha, racha $racha",
                    Toast.LENGTH_SHORT
                ).show()

                ctrlbd.insertarRegistro(fecha, racha)


                val rachaTexto = racha.toString()
                racha2.text = "$rachaTexto días"

                val rachaMaxima = ctrlbd.cargarMaxima()
                rachaMaxima.moveToFirst()

                val maxTexto = rachaMaxima.getString(rachaMaxima.getColumnIndexOrThrow("MAX(ultimaRacha)"))
                rachaMax2.text = "$maxTexto días"

                //se habilita el boton Nota
                btnNota2.isEnabled = true

//                // Reproducir sonido de éxito
//                val mediaPlayer = MediaPlayer.create(this, R.raw.victoria)
//                mediaPlayer.start()
//
//                // Liberar recursos al terminar
//                mediaPlayer.setOnCompletionListener {
//                    mediaPlayer.release()
//                }
            }

        }

        //boton Resumen para consultar un calendario con los dias marcados
        btnResume2.setOnClickListener{
            var intent = Intent(this, CalendarioRacha::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("tabla", "habito2")
            startActivity(intent)
        }

        btnNota2.setOnClickListener{
            var intent = Intent(this, Nota::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("tabla", "habito2")
            startActivity(intent)
        }


        //HABITO 3

        //Componentes del habito1
        val btnRacha3 = findViewById<Button>(R.id.btnAcumular3)
        val btnResume3 = findViewById<Button>(R.id.btnResumen3)
        val racha3 = findViewById<TextView>(R.id.contadorRacha3)
        val rachaMax3 = findViewById<TextView>(R.id.rachaMax3)
        val nombre3 = findViewById<TextView>(R.id.tituloHabito3)
        val img3 = findViewById<ImageView>(R.id.imgRachaOk3)
        val btnNota3 = findViewById<Button>(R.id.btnNota3)

        //carga datos iniciales de base de datos de habito1
        val ctrlbd3 = CtrlBaseDatos(this, usuario, null, 1, "habito3")
        val cursor3 = ctrlbd3.cargarDatos()
        cursor3.moveToFirst()
        nombre3.text = cursor3.getString(cursor3.getColumnIndexOrThrow("nombre"))
        val imagen3 = cursor3.getString(cursor3.getColumnIndexOrThrow("imagen"))

        //se carga desde recursos la imagen segun el nombre almacenado en base de datos
        when (imagen3) {
            "imagen0" -> img3.setImageResource(R.drawable.imagen0)
            "imagen1" -> img3.setImageResource(R.drawable.imagen1)
            "imagen2" -> img3.setImageResource(R.drawable.imagen2)
            "imagen3" -> img3.setImageResource(R.drawable.imagen3)
        }

        //se recupera la ultima racha acumulada
        val ultimaRacha3 = ctrlbd3.cargarRacha()
        ultimaRacha3.moveToFirst()
        val ultimaRachaTexto3 = ultimaRacha3.getString(ultimaRacha3.getColumnIndexOrThrow("ultimaRacha"))
        if(comprobarRacha("habito3", usuario) || habilitarNota("habito3", usuario)){
            racha3.text = "$ultimaRachaTexto3 días"
        }else{
            racha3.text = "0 días"

//            // Reproducir sonido de éxito
//            val mediaPlayer = MediaPlayer.create(this, R.raw.derrota)
//            mediaPlayer.start()
//
//            // Liberar recursos al terminar
//            mediaPlayer.setOnCompletionListener {
//                mediaPlayer.release()
//            }
        }


        //se recupera la racha maxima desde que se creo el habito
        val rachaMaxima3 = ctrlbd3.cargarMaxima()
        rachaMaxima3.moveToFirst()

        val maxTexto3 = rachaMaxima3.getString(rachaMaxima3.getColumnIndexOrThrow("MAX(ultimaRacha)"))
        rachaMax3.text = "$maxTexto3 días"

        //se activa el boton Nota
        if(habilitarNota("habito3",usuario)){
            btnNota3.isEnabled = true
        }else{
            btnNota3.isEnabled = false
        }

        //boton OK para sumar un dia a la racha y registrarlo en base de datos
        btnRacha3.setOnClickListener {
            val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, "habito3")
            var bd = ctrlbd.writableDatabase
            val cursor = ctrlbd.cargarDatos()
            cursor.moveToFirst()

            val rachaInt = cursor.getString(cursor.getColumnIndexOrThrow("ultimaRacha")).toInt()
            var racha = 0;
            var ultimaFecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))


            val hoy = LocalDateTime.now()
            val dia = hoy.dayOfMonth
            var mes = hoy.monthValue - 1
            if (mes == -1) {
                mes = 11
            }
            val year = hoy.year

            val fecha = "$dia-$mes-$year"

            if (ultimaFecha.equals(fecha)) {
                Toast.makeText(
                    this,
                    "Tu racha esta al dia!!! Mañana más!!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if (comprobarRacha("habito3", usuario)) {
                    racha = rachaInt + 1;
                } else {
                    racha = 1
                }
                Toast.makeText(
                    this,
                    "2 fecha $ultimaFecha, racha $racha",
                    Toast.LENGTH_SHORT
                ).show()

                ctrlbd.insertarRegistro(fecha, racha)


                val rachaTexto = racha.toString()
                racha3.text = "$rachaTexto días"

                val rachaMaxima = ctrlbd.cargarMaxima()
                rachaMaxima.moveToFirst()

                val maxTexto = rachaMaxima.getString(rachaMaxima.getColumnIndexOrThrow("MAX(ultimaRacha)"))
                rachaMax3.text = "$maxTexto días"

                //se habilita el boton Nota
                btnNota3.isEnabled = true

                // Reproducir sonido de éxito
                val mediaPlayer = MediaPlayer.create(this, R.raw.victoria)
                mediaPlayer.start()

                // Liberar recursos al terminar
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                }
            }

        }

        //boton Resumen para consultar un calendario con los dias marcados
        btnResume3.setOnClickListener{
            var intent = Intent(this, CalendarioRacha::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("tabla", "habito3")
            startActivity(intent)
        }

        btnNota3.setOnClickListener{
            var intent = Intent(this, Nota::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("tabla", "habito3")
            startActivity(intent)
        }

    }

    //Para recuperar el dia de hoy con LocalDateTime el IDE me pide subir el SDK minimo a 26, lo cambio en fichero de gradle
    fun comprobarRacha(habito: String, usuario: String): Boolean {
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito)
        val cursor = ctrlbd.cargarDatos()

        val ayer = LocalDateTime.now().minusDays(1)

        val diaAyer = ayer.dayOfMonth
        var mesAyer = ayer.monthValue - 1
        if (mesAyer == -1) {
            mesAyer = 11
        }
        val yearAyer = ayer.year


        if (cursor.moveToFirst()) {
            val ultimaFecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))

            val diaUlt = Integer.parseInt(ultimaFecha.split("-")[0])
            val mesUlt = Integer.parseInt(ultimaFecha.split("-")[1])
            val yearUlt = Integer.parseInt(ultimaFecha.split("-")[2])

            return diaAyer == diaUlt
                    && mesAyer == mesUlt
                    && yearAyer == yearUlt
        }
        return false
    }

    fun habilitarNota(habito: String, usuario: String): Boolean {
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, habito)
        val cursor = ctrlbd.cargarDatos()

        val ayer = LocalDateTime.now()

        val diaHoy = ayer.dayOfMonth
        var mesHoy = ayer.monthValue - 1
        if (mesHoy == -1) {
            mesHoy = 11
        }
        val yearHoy = ayer.year


        if (cursor.moveToFirst()) {
            val ultimaFecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
            val diaUlt = Integer.parseInt(ultimaFecha.split("-")[0])
            val mesUlt = Integer.parseInt(ultimaFecha.split("-")[1])
            val yearUlt = Integer.parseInt(ultimaFecha.split("-")[2])

            return diaHoy == diaUlt
                    && mesHoy == mesUlt
                    && yearHoy == yearUlt
        }
        return false
    }

}
