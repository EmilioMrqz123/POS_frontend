package com.example.proyectofinal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vehiculo(
    val id: String,          // _id de MongoDB
    val modelo: String,
    val detalles: String,
    val precio: String,
    val imagenUrl: String    // URL para Glide. Si pruebas local, puedes dejarlo vacío.
) : Parcelable