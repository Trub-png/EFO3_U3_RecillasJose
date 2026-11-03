package com.example.actividad7_basesdedatos.network

import com.example.actividad7_basesdedatos.mode.ITunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

//ARCHIVOS PARA LA CONSULTA DE INFORMACIÓN (LOCAL O INTERNET)
interface ITunesApi {
    //Indicar método HTTP a usar 22/10/2026
    @GET("search")
    //Meétodo para obtener la información con la URL del API
    fun obtenerAlbumGenero(
        @Query("term") genero: String,
        @Query("country") pais: String,
        @Query("media") media: String,
        @Query("entity") entidad: String,
        @Query("limit") limite: Int,

    ) : retrofit2.Call<ITunesResponse>

}