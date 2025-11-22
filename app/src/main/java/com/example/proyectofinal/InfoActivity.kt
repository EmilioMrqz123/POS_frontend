package com.example.proyectofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        // Configurar la Toolbar para tener el botón de regreso
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_info)
        setSupportActionBar(toolbar)

        // Habilitar la flecha de atrás
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Hacer que la flecha funcione
        toolbar.setNavigationOnClickListener {
            finish() // Cierra esta actividad y vuelve al menú principal
        }
    }
}