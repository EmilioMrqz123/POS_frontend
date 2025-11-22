package com.example.proyectofinal

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class CatalogoActivity : AppCompatActivity() {

    private lateinit var adapter: VehiculoAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    // CAMBIA ESTO A 'false' CUANDO YA TENGAS TU API DE MONGODB LISTA
    private val MODO_PRUEBA_LOCAL = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        // Inicializar Vistas
        progressBar = findViewById(R.id.progressBar_carga)
        recyclerView = findViewById(R.id.rv_vehiculos_compra)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar Toolbar (Opcional)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_catalogo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // DECISIÓN: ¿Cargamos datos falsos o reales?
        if (MODO_PRUEBA_LOCAL) {
            cargarDatosDePrueba()
        } else {
            cargarDatosDeMongoDB()
        }
    }

    // ---------------------------------------------------------
    // OPCIÓN A: DATOS DE PRUEBA (Para probar HOY sin servidor)
    // ---------------------------------------------------------
    private fun cargarDatosDePrueba() {
        val listaFalsa = listOf(
            Vehiculo("1", "Honda Civic", "2022 | Turbo", "$ 450,000", ""),
            Vehiculo("2", "Ford Lobo", "2023 | 4x4", "$ 1,200,000", ""),
            Vehiculo("3", "Nissan Versa", "2021 | Austero", "$ 280,000", "")
        )

        adapter = VehiculoAdapter(listaFalsa)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE
    }

    // ---------------------------------------------------------
    // OPCIÓN B: DATOS REALES (Cuando tengas la API lista)
    // ---------------------------------------------------------
    private fun cargarDatosDeMongoDB() {
        progressBar.visibility = View.VISIBLE

        // Asegúrate de que ConexionWeb.kt esté creado
        ConexionWeb.getInstance(this).executeGetRequest("obtener_vehiculos.php",
            { response ->
                progressBar.visibility = View.GONE
                procesarJSON(response)
            },
            { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                // Si falla internet, cargamos los de prueba por seguridad?
                // cargarDatosDePrueba()
            }
        )
    }

    private fun procesarJSON(jsonString: String) {
        try {
            val listaVehiculos = ArrayList<Vehiculo>()
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val vehiculo = Vehiculo(
                    id = obj.optString("_id"),
                    modelo = obj.optString("modelo"),
                    detalles = obj.optString("detalles"),
                    precio = obj.optString("precio"),
                    imagenUrl = obj.optString("imagen_url")
                )
                listaVehiculos.add(vehiculo)
            }
            adapter = VehiculoAdapter(listaVehiculos)
            recyclerView.adapter = adapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}