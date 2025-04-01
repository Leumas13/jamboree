package com.example.habitos

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class CtrlBaseDatos(context: Context?, name: String?, factory: CursorFactory?, version: Int, tabla:String) :
    SQLiteOpenHelper(context, name, factory, version) {

        val TABLE_NAME = tabla

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $TABLE_NAME (indice int primary key, fecha text, nombre text, imagen text, usuario text, pass text, ultimaRacha int, nota text)")

    }

    //creo un onCreate con el parametro tabla para crear las tablas de habito1 y habito2
    fun onCreate(db: SQLiteDatabase, tabla: String) {
        db.execSQL("create table $tabla (indice int primary key, fecha text, nombre text, imagen text, usuario text, pass text, ultimaRacha int, nota text)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $TABLE_NAME")
        onCreate(db)
    }

    //Función para recuperar datos
    fun cargarDatos(): Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY 1 DESC", null)
    }

    fun cargarMaxima(): Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT MAX(ultimaRacha) FROM $TABLE_NAME ORDER BY 1 DESC", null)
    }

    fun cargarRacha(): Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT MAX(indice), ultimaRacha FROM $TABLE_NAME", null)
    }


    fun insertarRegistro(fecha:String, ultimaRacha:Int){
        val bd = this.writableDatabase
        val reg = ContentValues()

        val cursor = this.cargarDatos()
        cursor.moveToFirst()

        val indiceInt = cursor.getString(cursor.getColumnIndexOrThrow("indice")).toInt()
        val indice = indiceInt + 1;
        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
        val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
        val usuario = cursor.getString(cursor.getColumnIndexOrThrow("usuario"))
        val pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"))


        reg.put("indice", indice)
        reg.put("fecha", fecha)
        reg.put("nombre", nombre)
        reg.put("imagen", imagen)
        reg.put("usuario", usuario)
        reg.put("pass", pass)
        reg.put("ultimaRacha", ultimaRacha)
        reg.put("nota", "")
        bd.insert(TABLE_NAME, null, reg)
    }

    fun insertarNota(nota:String, fecha:String){
        val bd = this.writableDatabase
        val reg = ContentValues()

        reg.put("nota", nota)
        bd.update(TABLE_NAME,reg,"fecha = '$fecha'",null)
    }

    fun cargarDatosPrueba1(){

        val bd = this.writableDatabase
        val reg = ContentValues()

        //datos prueba habito1

            reg.put("indice", 1)
            reg.put("fecha", "1-0-2025")
            reg.put("nombre", "algo1")
            reg.put("imagen", "imagen1")
            reg.put("usuario", "prueba")
            reg.put("pass", "pass")
            reg.put("ultimaRacha", 1)
        reg.put("nota", "nota1")
            bd.insert("habito1", null, reg)

        reg.put("indice", 2)
        reg.put("fecha", "3-0-2025")
        reg.put("nombre", "algo1")
        reg.put("imagen", "imagen1")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota2")
        bd.insert("habito1", null, reg)

        reg.put("indice", 3)
        reg.put("fecha", "4-0-2025")
        reg.put("nombre", "algo1")
        reg.put("imagen", "imagen1")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 2)
        reg.put("nota", "nota3")
        bd.insert("habito1", null, reg)

        reg.put("indice", 4)
        reg.put("fecha", "5-0-2025")
        reg.put("nombre", "algo1")
        reg.put("imagen", "imagen1")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 3)
        reg.put("nota", "nota4")
        bd.insert("habito1", null, reg)

        reg.put("indice", 5)
        reg.put("fecha", "6-0-2025")
        reg.put("nombre", "algo1")
        reg.put("imagen", "imagen1")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 4)
        reg.put("nota", "nota5")
        bd.insert("habito1", null, reg)

        reg.put("indice", 6)
        reg.put("fecha", "15-0-2025")
        reg.put("nombre", "algo1")
        reg.put("imagen", "imagen1")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota6")
        bd.insert("habito1", null, reg)


        //datos prueba habito2

        reg.put("indice", 1)
        reg.put("fecha", "1-0-2025")
        reg.put("nombre", "algo2")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota1")
        bd.insert("habito2", null, reg)

        reg.put("indice", 2)
        reg.put("fecha", "3-0-2025")
        reg.put("nombre", "algo2")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota2")
        bd.insert("habito2", null, reg)

        reg.put("indice", 3)
        reg.put("fecha", "4-0-2025")
        reg.put("nombre", "algo2")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 2)
        reg.put("nota", "nota3")
        bd.insert("habito2", null, reg)

        reg.put("indice", 4)
        reg.put("fecha", "5-0-2025")
        reg.put("nombre", "algo2")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 3)
        reg.put("nota", "nota4")
        bd.insert("habito2", null, reg)

        reg.put("indice", 5)
        reg.put("fecha", "6-0-2025")
        reg.put("nombre", "algo2")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 4)
        reg.put("nota", "nota5")
        bd.insert("habito2", null, reg)

        reg.put("indice", 6)
        reg.put("fecha", "13-0-2025")
        reg.put("nombre", "algo2")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota6")
        bd.insert("habito2", null, reg)

        //datos prueba habito3

        reg.put("indice", 1)
        reg.put("fecha", "10-0-2025")
        reg.put("nombre", "algo3")
        reg.put("imagen", "imagen3")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota1")
        bd.insert("habito3", null, reg)

        reg.put("indice", 2)
        reg.put("fecha", "11-0-2025")
        reg.put("nombre", "algo3")
        reg.put("imagen", "imagen3")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 2)
        reg.put("nota", "nota2")
        bd.insert("habito3", null, reg)

        reg.put("indice", 3)
        reg.put("fecha", "12-0-2025")
        reg.put("nombre", "algo3")
        reg.put("imagen", "imagen3")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 3)
        reg.put("nota", "nota3")
        bd.insert("habito3", null, reg)

        reg.put("indice", 4)
        reg.put("fecha", "13-0-2025")
        reg.put("nombre", "algo3")
        reg.put("imagen", "imagen3")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 4)
        reg.put("nota", "nota4")
        bd.insert("habito3", null, reg)

        reg.put("indice", 5)
        reg.put("fecha", "14-0-2025")
        reg.put("nombre", "algo3")
        reg.put("imagen", "imagen3")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 5)
        reg.put("nota", "nota5")
        bd.insert("habito3", null, reg)

        reg.put("indice", 6)
        reg.put("fecha", "15-0-2025")
        reg.put("nombre", "algo3")
        reg.put("imagen", "imagen3")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 6)
        reg.put("nota", "nota6")
        bd.insert("habito3", null, reg)


        bd.close()
    }

    fun cargarDatosPrueba2(){

        val bd = this.writableDatabase
        val reg = ContentValues()



        //datos prueba habito2

        reg.put("indice", 1)
        reg.put("fecha", "1-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota1")
        bd.insert("habito2", null, reg)

        reg.put("indice", 2)
        reg.put("fecha", "3-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota2")
        bd.insert("habito2", null, reg)

        reg.put("indice", 3)
        reg.put("fecha", "4-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 2)
        reg.put("nota", "nota3")
        bd.insert("habito2", null, reg)

        reg.put("indice", 4)
        reg.put("fecha", "5-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 3)
        reg.put("nota", "nota4")
        bd.insert("habito2", null, reg)

        reg.put("indice", 5)
        reg.put("fecha", "6-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 4)
        reg.put("nota", "nota5")
        bd.insert("habito2", null, reg)

        reg.put("indice", 6)
        reg.put("fecha", "9-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota6")
        bd.insert("habito2", null, reg)


        bd.close()
    }

    fun cargarDatosPrueba3(){

        val bd = this.writableDatabase
        val reg = ContentValues()



        //datos prueba habito3

        reg.put("indice", 1)
        reg.put("fecha", "1-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota1")
        bd.insert("habito3", null, reg)

        reg.put("indice", 2)
        reg.put("fecha", "3-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota2")
        bd.insert("habito3", null, reg)

        reg.put("indice", 3)
        reg.put("fecha", "4-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 2)
        reg.put("nota", "nota3")
        bd.insert("habito3", null, reg)

        reg.put("indice", 4)
        reg.put("fecha", "5-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 3)
        reg.put("nota", "nota4")
        bd.insert("habito3", null, reg)

        reg.put("indice", 5)
        reg.put("fecha", "6-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 4)
        reg.put("nota", "nota5")
        bd.insert("habito3", null, reg)

        reg.put("indice", 6)
        reg.put("fecha", "9-0-2025")
        reg.put("nombre", "algo")
        reg.put("imagen", "imagen2")
        reg.put("usuario", "prueba")
        reg.put("pass", "pass")
        reg.put("ultimaRacha", 1)
        reg.put("nota", "nota6")
        bd.insert("habito3", null, reg)


        bd.close()
    }




}
