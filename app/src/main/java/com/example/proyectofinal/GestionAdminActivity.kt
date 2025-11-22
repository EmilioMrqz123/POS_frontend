package com.example.proyectofinal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GestionAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_admin)

        val fabAgregar: FloatingActionButton = findViewById(R.id.fab_agregar_vehiculo)

        fabAgregar.setOnClickListener {
            // Aquí iría el Intent para abrir un formulario de "Agregar Vehículo"
            Toast.makeText(this, "Abrir formulario de alta...", Toast.LENGTH_SHORT).show()
        }

        // Aquí deberías cargar el RecyclerView igual que en el Catálogo,
        // pero quizás con botones extra de "Editar" y "Eliminar" en cada item.
    }
}