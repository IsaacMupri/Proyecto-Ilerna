package com.example.proyectoilerna.screens.Pedidos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectoilerna.mode.Producto
import com.example.proyectoilerna.util.AuthManager
import com.example.proyectoilerna.util.DataViewModel

@Composable
fun PedidoScreen(dataViewModel: DataViewModel = viewModel()) {
    val productList = dataViewModel.state.value

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
                    ProductoCard(product = product)
                }
            }
        }
        Button(
            onClick = {
                val idUsuario = AuthManager().getCurrentUser()
//                dataViewModel.insertarPedido(nuevoPedido)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Enviar Pedido")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoCard(product: Producto) {
    var quantity by remember { mutableIntStateOf(0) }
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
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
                    }

                    Text(text = quantity.toString())

                    IconButton(
                        onClick = {
                            quantity++
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PedidoScreenPreview() {
    val sampleProducts = List(5) {
        Producto("Producto $it")
    }
    PedidoScreen(dataViewModel = DataViewModel().apply {
        state.value = sampleProducts
    })
}
