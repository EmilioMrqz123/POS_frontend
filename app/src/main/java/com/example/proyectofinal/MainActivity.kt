package com.example.proyectofinal
import android.content.Context
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