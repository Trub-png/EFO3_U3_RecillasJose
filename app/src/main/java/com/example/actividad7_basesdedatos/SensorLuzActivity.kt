package com.example.actividad7_basesdedatos

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SensorLuzActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var layoutPrincipal: LinearLayout
    private lateinit var tvTitulo: TextView
    private lateinit var tvValorLuz: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_luz)

        layoutPrincipal = findViewById(R.id.layoutPrincipal)
        tvTitulo = findViewById(R.id.tvTituloSensor)
        tvValorLuz = findViewById(R.id.tvValorLuz)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            tvValorLuz.text = "No hay sensor de luz en este dispositivo"
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val luz = event.values[0]
            tvValorLuz.text = "Luz actual: $luz lux"

            // Ejemplo básico usando setBackgroundColor y setTextColor como pide la actividad
            if (luz < 50) {
                // Oscuro - fondo negro, texto blanco
                layoutPrincipal.setBackgroundColor(Color.BLACK)
                tvTitulo.setTextColor(Color.WHITE)
                tvValorLuz.setTextColor(Color.WHITE)
            } else {
                // Claro - fondo blanco, texto negro
                layoutPrincipal.setBackgroundColor(Color.WHITE)
                tvTitulo.setTextColor(Color.BLACK)
                tvValorLuz.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No requerido para este ejemplo básico
    }
}
