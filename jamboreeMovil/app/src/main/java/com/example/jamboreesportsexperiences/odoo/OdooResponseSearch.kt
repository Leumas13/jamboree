package com.example.jamboreesportsexperiences.odoo

data class OdooResponseSearch<T>(
    //Lista de daqtos que devuele odoo
    val result: List<T>?
)
