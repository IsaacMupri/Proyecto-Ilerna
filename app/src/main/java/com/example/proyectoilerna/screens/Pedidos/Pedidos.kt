package com.example.proyectoilerna.screens.Pedidos

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectoilerna.mode.Producto
import com.example.proyectoilerna.util.AuthManager
import com.example.proyectoilerna.util.DataViewModel

@Composable
fun PedidoScreen(dataViewModel: DataViewModel = viewModel()) {
    val productList = dataViewModel.state.value
    val pedidosViewModel = viewModel<PedidosViewModel>()
    val context = LocalContext.current
    var isEnviado by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Haz tu Pedido",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn {
                items(productList) { product ->
                    ProductoCard(product = product, isEnviado)
                }
            }
        }
        Button(
            onClick = {
                val idUsuario = AuthManager().getCurrentUser()?.uid
                val pedidoList = pedidosViewModel.pedidoList

                val pedidoMap = mapOf(
                    "idUsuario" to idUsuario,
                    "productosPedidos" to pedidoList.map { it.toMap() })
                dataViewModel.insertarPedido(pedidoMap)
                pedidosViewModel.eliminarTodosLosPedidos()
                Toast.makeText(context, "Pedido enviado", Toast.LENGTH_SHORT).show()
                isEnviado = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Enviar Pedido")
        }
    }
    LaunchedEffect(isEnviado) {
        if (isEnviado) {
            isEnviado = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoCard(product: Producto, isEnviado: Boolean) {
    val pedidosViewModel = viewModel<PedidosViewModel>()
    var quantity by remember { mutableIntStateOf(0) }

    if (isEnviado == true) {
        quantity = 0
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        border = BorderStroke(1.dp, Color.Gray),
        onClick = {
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = product.denominacion,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (quantity > 0) {
                                quantity--
                            }
                            pedidosViewModel.agregarOActualizarPedido(
                                PedidoElement(
                                    quantity,
                                    product.denominacion
                                )
                            )
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
                    }

                    Text(text = quantity.toString())

                    IconButton(
                        onClick = {
                            quantity++
                            pedidosViewModel.agregarOActualizarPedido(
                                PedidoElement(
                                    quantity,
                                    product.denominacion
                                )
                            )
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}


data class PedidoElement(val cantidad: Int, val denominacion: String) {
    fun toMap(): Map<String, Any> {
        return mapOf("cantidad" to cantidad, "denominacion" to denominacion)
    }
}

class PedidosViewModel : ViewModel() {
    private val _pedidoList = mutableStateListOf<PedidoElement>()
    val pedidoList: List<PedidoElement> get() = _pedidoList

    private fun agregarPedido(pedido: PedidoElement) {
        _pedidoList.add(pedido)
    }

    fun agregarOActualizarPedido(pedido: PedidoElement) {
        val index = _pedidoList.indexOfFirst { it.denominacion == pedido.denominacion }

        if (index != -1) {
            if (pedido.cantidad == 0) {
                eliminarPedido(pedido.denominacion)
            } else {
                _pedidoList[index] = pedido
            }
        } else {
            if (pedido.cantidad != 0) {
                agregarPedido(pedido)
            }
        }
    }

    fun eliminarPedido(denominacion: String) {
        _pedidoList.removeIf { it.denominacion == denominacion }
    }

    fun eliminarTodosLosPedidos() {
        _pedidoList.clear()
    }

}
