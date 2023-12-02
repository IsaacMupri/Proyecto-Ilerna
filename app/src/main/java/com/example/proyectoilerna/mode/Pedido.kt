package com.example.proyectoilerna.mode

import com.google.firebase.auth.FirebaseUser

data class Pedido(
    val uid: FirebaseUser? = null,
    val tblProducto: List<Pair<String, Int>>,
)