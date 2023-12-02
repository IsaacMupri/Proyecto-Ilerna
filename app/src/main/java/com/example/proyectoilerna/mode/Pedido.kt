package com.example.proyectoilerna.mode

data class Pedido(
    val key: String? = null,
    val uid: Int? = null,
    val tblProducto: List<Producto>,
)