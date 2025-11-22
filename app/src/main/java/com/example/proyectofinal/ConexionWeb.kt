package com.example.proyectofinal

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ConexionWeb private constructor(context: Context) {
    private val ctx: Context = context.applicationContext
    private var requestQueue: RequestQueue? = null

    init { requestQueue = getRequestQueue() }

    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx)
        }
        return requestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        getRequestQueue().add(req)
    }

    fun executeGetRequest(endpoint: String, listener: Response.Listener<String>, errorListener: Response.ErrorListener) {
        val url = URL_BASE + endpoint
        val getRequest = StringRequest(Request.Method.GET, url, listener, errorListener)
        addToRequestQueue(getRequest)
    }

    companion object {
        @Volatile
        private var instance: ConexionWeb? = null
        // IMPORTANTE: Cambia esta URL cuando tengas tu servidor
        private const val URL_BASE = "http://192.168.1.XX/gecas_api/"

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ConexionWeb(context).also { instance = it }
            }
    }
}