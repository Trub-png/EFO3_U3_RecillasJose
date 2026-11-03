package com.example.actividad7_basesdedatos.mode
//ARCHIVO PARA RECIBIR LA RESPUESTA DE CONFIRAMCIÓN DEL API
data class ITunesResponse(
    val resultCount : Int,
    val results : List<Album>
)
