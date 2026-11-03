package com.example.actividad7_basesdedatos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//ARCHIVO QUE PERMITE TENER UN OBJETO PARA LA CONEXIÓN A INTERNET
object RetrofitClient {
    //Contante para tener la BASE URL
    private const val BASE_URL = "https://itunes.apple.com/"

    //Variable que hereda de "lazy"
    val api : ITunesApi by lazy {
        //Construir la URL completa
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApi::class.java)
    }
}