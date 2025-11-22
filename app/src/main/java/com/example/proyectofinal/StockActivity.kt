package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        val etUsuario: TextInputEditText = findViewById(R.id.et_usuario_admin)
        val etPassword: TextInputEditText = findViewById(R.id.et_password_admin)
        val btnLogin: Button = findViewById(R.id.btn_login_admin)

        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            // VALIDACIÓN SIMPLE (Mocking)
            // En el futuro, esto conectará a tu MongoDB para verificar
            if (usuario == "admin" && password == "1234") {

                // Login correcto: Ir al Panel de Gestión
                val intent = Intent(this, GestionAdminActivity::class.java)
                startActivity(intent)
                finish() // Cierra el login para que no puedan volver atrás

            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}