package com.example.habitos

import android.content.Intent
import java.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import java.time.LocalDateTime

class CalendarioRacha : AppCompatActivity() {
    //Atributos de clase para manejar el calendario
    private lateinit var calendarView: CalendarView
    private var events: MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        //info usuario cuando llega desde otro activity
        val bundle:Bundle? = intent.extras
        var info = bundle?.getString("usuario")
        val usuario = info.toString()
        info = bundle?.getString("tabla")
        val tabla = info.toString()


        //Bloque menu
        val inicio = findViewById<ImageButton>(R.id.btnHome)
        val habitos = findViewById<ImageButton>(R.id.btnHabitos)
        val salir = findViewById<ImageButton>(R.id.btnSalir)

        inicio.setOnClickListener{
            var intent = Intent(this, Principal::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        habitos.setOnClickListener{
            var intent = Intent(this, NuevoHabito::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        salir.setOnClickListener{
            var intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }


        //Bloque activity

        //HABITO 1

        //Componentes
        var calendarView = findViewById<CalendarView>(R.id.calendar)
        val habito = findViewById<TextView>(R.id.tituloCalendario)
        val imagenCal = findViewById<ImageView>(R.id.imagenCalendario)
        val infoCal = findViewById<TextView>(R.id.dia)

        //Recuperar datos de fechas, nombre e imagen
        val ctrlbd = CtrlBaseDatos(this, usuario, null, 1, tabla)
        val cursor = ctrlbd.cargarDatos()
        cursor.moveToFirst()
        habito.text = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
        val imagen1 = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

        //se carga desde recursos la imagen segun el nombre almacenado en base de datos
        when (imagen1) {
            "imagen0" -> imagenCal.setImageResource(R.drawable.imagen0)
            "imagen1" -> imagenCal.setImageResource(R.drawable.imagen1)
            "imagen2" -> imagenCal.setImageResource(R.drawable.imagen2)
            "imagen3" -> imagenCal.setImageResource(R.drawable.imagen3)
        }



        //Modificar el calendario
        val calendars: ArrayList<CalendarDay> = ArrayList()

        //bucle para crear todos los dias que estan almacenados en la base de datos


        val cursorCalendario = ctrlbd.cargarDatos()

        while (cursorCalendario.moveToNext()) {

            val ultimaFecha = cursorCalendario.getString(cursorCalendario.getColumnIndexOrThrow("fecha"))
            val diaUlt = Integer.parseInt(ultimaFecha.split("-")[0])
            val mesUlt = Integer.parseInt(ultimaFecha.split("-")[1])
            val yearUlt = Integer.parseInt(ultimaFecha.split("-")[2])

            val nota = cursorCalendario.getString(cursorCalendario.getColumnIndexOrThrow("nota"))

            val calendar = Calendar.getInstance()
            calendar.set(yearUlt, mesUlt, diaUlt)

            val calendarDay = CalendarDay(calendar)
            calendarDay.labelColor = R.color.naranja

            val imagen1 = cursorCalendario.getString(cursorCalendario.getColumnIndexOrThrow("imagen"))


            //se carga desde recursos la imagen segun el nombre almacenado en base de datos
            when (imagen1) {
                "imagen0" -> calendarDay.imageResource = R.drawable.imagen0
                "imagen1" -> calendarDay.imageResource = R.drawable.imagen1
                "imagen2" -> calendarDay.imageResource = R.drawable.imagen2
                "imagen3" -> calendarDay.imageResource = R.drawable.imagen3
            }

            if(habilitarNota(tabla,usuario)){
                infoCal.text = "Enhorabuena! Estas al día!"
            }else{
                infoCal.text = "Ánimo, a por un día más!"
            }

            //agregar a la lista de calendarios el dia creado
            calendars.add(calendarDay)
            //agregar nota a la fecha
            events["$diaUlt-$mesUlt-$yearUlt"] = nota

        }

        calendarView.setCalendarDays(calendars)
        calendarView.setOnCalendarDayClickListener(object: OnCalendarDayClickListener{

            //mostrar nota
            override fun onClick(calendarDay: CalendarDay) {
                val day = calendarDay.calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendarDay.calendar.get(Calendar.MONTH)
                val year = calendarDay.calendar.get(Calendar.YEAR)
                if(events.containsKey("$day-$month-$year")){
                    Toast.makeText(baseContext, events["$day-$month-$year"], Toast.LENGTH_SHORT).show()
                }else{
                    // Toast.makeText(baseContext, "Nada que hacer", Toast.LENGTH_SHORT).show()
                    Toast.makeText(baseContext, "$day-$month-$year", Toast.LENGTH_SHORT).show()
                }
            }
        })
        calendarView.setOnPreviousPageChangeListener(object: OnCalendarPageChangeListener{
            override fun onChange() {
                val month = String.format("%02d", calendarView.currentPageDate.get(Calendar.MONTH))
                val year = calendarView.currentPageDate.get(Calendar.YEAR)
                Toast.makeText(baseContext, "$month/$year", Toast.LENGTH_SHORT).show()

            }
        })

        calendarView.setOnForwardPageChangeListener(object: OnCalendarPageChangeListener{
            override fun onChange() {
                val month = String.format("%02d", calendarView.currentPageDate.get(Calendar.MONTH))
                val year = calendarView.currentPageDate.get(Calendar.YEAR)
                Toast.makeText(baseContext, "$month/$year", Toast.LENGTH_SHORT).show()

            }
        })




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