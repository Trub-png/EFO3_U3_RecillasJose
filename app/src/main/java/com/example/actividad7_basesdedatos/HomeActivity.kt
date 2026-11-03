package com.example.actividad7_basesdedatos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.actividad7_basesdedatos.mode.Album
import com.example.actividad7_basesdedatos.mode.ITunesResponse
import com.example.actividad7_basesdedatos.network.ITunesApi
import com.example.actividad7_basesdedatos.network.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var tvResultado : TextView
    private lateinit var contenedorIndie : LinearLayout

    private lateinit var contenedorRock : LinearLayout
    private lateinit var contenedorTop : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvResultado = findViewById(R.id.tvResultado)
        contenedorIndie = findViewById(R.id.contenedorIndie)
        contenedorRock = findViewById(R.id.contenedorRock)
        contenedorTop = findViewById(R.id.contenedorTop)

        val btnSensorLuz = findViewById<Button>(R.id.btnSensorLuz)
        btnSensorLuz.setOnClickListener {
            val intent = Intent(this, SensorLuzActivity::class.java)
            startActivity(intent)
        }

        //TODO: Agregar método de llamado de álbum
        obtenerAlbum("Indie",contenedorIndie)
        obtenerAlbum("Rocck",contenedorRock)
        obtenerAlbum("Top",contenedorTop)

    }

    //MÉTODO PARA OBTENER INFORMACIÓN DEL ALBUM
    private fun obtenerAlbum(genero : String, contenedor : LinearLayout){
        //Hacer el llamado a la construcción de la URL
        RetrofitClient.api.obtenerAlbumGenero(
            genero = genero,
            pais = "MX",
            media = "music",
            entidad = "album",
            limite = 20
        ).enqueue(object : Callback<ITunesResponse>{
            //MÉTODO EN CASO DE QUE LA RESPUESTA SEA CORRECTA
            override fun onResponse(call: Call<ITunesResponse?>, response: Response<ITunesResponse?>){
                //Variable para guardae los albunes
                //variable que gusrda una lista de tipo Album
                val albumes = response.body()?.results ?: emptyList()
                //VALIDAR SI LA VARIABLE ALBUNES TIENE INDFORMACION
                if(albumes.isNotEmpty()){
                    tvResultado.visibility = View.GONE
                    mostrarAlbumes(albumes,contenedor)
                }else{
                    Toast.makeText(this@HomeActivity, "No hay respuesta", Toast.LENGTH_SHORT).show()

                }
            }
            //método para hacer una accion si el api falla
            override fun onFailure(call: Call<ITunesResponse?>, t: Throwable){
                Toast.makeText(this@HomeActivity, "Error en el API", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Metodo para mostrar el listado de datos de un API

    private fun mostrarAlbumes(albumes : List<Album>, contenedor: LinearLayout){
        //OBLIGATORIO: Evitar elementos duplicados
        contenedor.removeAllViews()
        // es contenedorIndie

        //Ciclo para recorrer las multiples peticiones a un API
        for(album in albumes){

            //Usar el LinearLayout con el contenedor
            val vistaAlbum = layoutInflater.inflate(
                R.layout.item_album,
                contenedor,
                // es contenedorIndie
                false
            )

            val imgPortada = vistaAlbum.findViewById<ImageView>(R.id.imgPortadaAlbum)
            val tvNombreAlbum = vistaAlbum.findViewById<TextView>(R.id.tvNombreAlbum)
            val tvNombreArtista = vistaAlbum.findViewById<TextView>(R.id.tvNombreArtista)

            //Asignando los nombre obtenidos del API
            tvNombreAlbum.text = album.collectionName ?: "Album sin nombre"
            tvNombreArtista.text = album.artistName ?: "Artista desconocido"

            //Mostrar la imagen del album
            Glide.with(this@HomeActivity)
                .load(album.obtenerImagenGrande())
                .into(imgPortada)




            vistaAlbum.setOnClickListener {
                //Necesitamos obtener el id

                val idAlbum = album.collectionId

                //validar para saber que se cuenta con ID
                if(idAlbum != null){
                    val intent = Intent(this@HomeActivity, DetailsActivity::class.java)

                    //Enviar datos
                    intent.putExtra("ID_ALBUM", idAlbum)
                    intent.putExtra("NOMBRE_ALBUM", album.collectionName)
                    intent.putExtra("ARTISTA_ALBUM", album.artistName)
                    intent.putExtra("GENERO_ALBUM", album.primaryGenreName)
                    intent.putExtra("CANCIONES_ALBUM", album.trackCount)
                    intent.putExtra("IMAGEN_ALBUM", album.obtenerImagenGrande())

                    startActivity(intent)
                }



            }
            //Asignar la vista del contenedor a las tarjetas de albumes
            contenedor.addView(vistaAlbum)



        }



    }




}