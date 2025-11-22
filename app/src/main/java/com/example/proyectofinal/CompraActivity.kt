package com.example.proyectofinal

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CompraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)

        // 1. Obtener el vehículo pasado en el Intent
        @Suppress("DEPRECATION")
        val vehiculoSeleccionado = intent.getParcelableExtra(Constants.VEHICULO_SELECCIONADO) as? Vehiculo

        // 2. Si el vehículo existe, actualizar las vistas del formulario
        if (vehiculoSeleccionado != null) {
            val tvVehiculoSeleccionado: TextView = findViewById(R.id.tv_vehiculo_seleccionado)
            val etMontoFinal: TextView = findViewById(R.id.et_monto_final)

            // Actualizar el modelo en el TextView del formulario
            tvVehiculoSeleccionado.text = "Vehículo: ${vehiculoSeleccionado.modelo} (${vehiculoSeleccionado.detalles})"

            // Actualizar el precio en el EditText del formulario
            // Aquí se recomienda convertir el String de precio a un formato numérico si es posible.
            etMontoFinal.text = vehiculoSeleccionado.precio.replace("[^\\d\\.,]".toRegex(), "")
            // Esto es una limpieza simple, puedes necesitar una lógica más robusta
        }
    }
}