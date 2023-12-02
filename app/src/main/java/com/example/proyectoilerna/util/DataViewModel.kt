package com.example.proyectoilerna.util

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoilerna.mode.Pedido
import com.example.proyectoilerna.mode.Producto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataViewModel : ViewModel() {
    val state = mutableStateOf<List<Producto>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getProductos()
        }
    }

    fun insertarPedido(pedido: Pedido) {
        viewModelScope.launch {
            insertPedido(pedido)
        }
    }
}

suspend fun getProductos(): List<Producto> {
    val db = FirebaseFirestore.getInstance()
    var productList: List<Producto> = emptyList()

    try {
        productList = db.collection("producto")
            .get()
            .await()
            .toObjects(Producto::class.java)
    } catch (e: FirebaseFirestoreException) {
        Log.d("error", "getDataFromFireStore: $e")
    }
    return productList
}

suspend fun insertPedido(pedido: Pedido) {
    val db = FirebaseFirestore.getInstance()

    try {
        db.collection("pedido")
            .add(pedido)
            .await()
    } catch (e: FirebaseFirestoreException) {
        Log.d("error", "insertPedido: $e")
    }
}
