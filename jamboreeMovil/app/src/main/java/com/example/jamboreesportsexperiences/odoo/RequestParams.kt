package com.example.jamboreesportsexperiences.odoo

data class RequestParams(
    val service: String,
    val method: String, // El método que se va a ejecutar ejecutar, por ejemplo, "search_read"
    val args: List<Any>, // Argumentos que el método necesita para funcionar (filtros y campos)
    val kwargs: Map<String, Any> // Parámetros adicionales como los campos que se quieren recuperar
)

