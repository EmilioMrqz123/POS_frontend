package com.example.proyectofinal
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ConexionMongoDB private constructor(context: Context) {
    private val ctx: Context = context.applicationContext
    private var requestQueue: RequestQueue? = null

    init {
        // Inicializa la cola de peticiones
        requestQueue = getRequestQueue()
    }

    fun getRequestQueue(): RequestQueue {
        // Crea la cola de peticiones si es nula
        if (requestQueue == null) {
            // Instancia la cola de peticiones con el contexto de la aplicación
            requestQueue = Volley.newRequestQueue(ctx)
        }
        // Retorna la cola
        return requestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        // Agrega la petición a la cola de Volley
        getRequestQueue().add(req)
    }

    fun executePostRequest(
        endpoint: String,
        bodyParams: MutableMap<String, String>,
        listener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ) {
        // Concatena la URL con el endpoint
        val url = URL_BASE + endpoint

        // Crea la petición StringRequest
        val postRequest = object : StringRequest(Method.POST, url, listener, errorListener) {
            // Sobrescribe el método para enviar parámetros POST
            override fun getParams(): MutableMap<String, String> {
                // Retorna los parámetros
                return bodyParams
            }
        }

        // Ejecuta la petición en la cola
        addToRequestQueue(postRequest)
    }

    companion object {
        @Volatile
        private var instance: ConexionMongoDB? = null

        private const val URL_BASE = "http://[TU_IP_O_DOMINIO]/gecas_api/"

        fun getInstance(context: Context) =
            // Crea la instancia de forma thread-safe
            instance ?: synchronized(this) {
                // Inicializa la instancia si es nula
                instance ?: ConexionMongoDB(context).also { instance = it }
            }
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita Edge-to-Edge, requiere que el layout raíz tenga el ID 'main'
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // El error R.id.main se resuelve asumiendo que añadiste android:id="@+id/main"
        // a tu ConstraintLayout raíz en activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Obtener referencias a los ImageButton por su ID
        val btnCatalogo: ImageButton = findViewById(R.id.btn_Catalogo)
        val btnCompra: ImageButton = findViewById(R.id.btn_Compra)
        val btnStock: ImageButton = findViewById(R.id.btn_Stock)
        val btnInfo: ImageButton = findViewById(R.id.btn_Info)

        // 2. Configurar Listeners (Eventos Clic) para cada botón

        // Intent para Catálogo de Vehículos
        btnCatalogo.setOnClickListener {
            iniciarActividad(CatalogoActivity::class.java)
        }

        // Intent para Proceso de Compra
        btnCompra.setOnClickListener {
            iniciarActividad(CompraActivity::class.java)
        }

        // Intent para Gestión de Stock
        btnStock.setOnClickListener {
            iniciarActividad(StockActivity::class.java)
        }

        // Intent para Información GECAS
        btnInfo.setOnClickListener {
            iniciarActividad(InfoActivity::class.java)
        }
    }

    /**
     * Función auxiliar genérica para iniciar cualquier actividad.
     * @param destino La clase de la Actividad a la que se desea navegar.
     */
    private fun iniciarActividad(destino: Class<*>) {
        val intent = Intent(this, destino)
        startActivity(intent)
        //
    }
}
object Constants {
    const val VEHICULO_SELECCIONADO = "vehiculo_seleccionado"
}