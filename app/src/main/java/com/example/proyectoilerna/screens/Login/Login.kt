package com.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.components.ClickableTextComponentRegistro
import com.components.HeaderTextComponent
import com.components.NormalTextComponent
import com.components.PasswordTextField
import com.components.textField
import com.example.proyectoilerna.util.AuthManager
import com.example.proyectoilerna.util.AuthRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController, auth: AuthManager) {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("isaac.mp92@gmail.com") }
    var password by remember { mutableStateOf("Gateway7788") }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            NormalTextComponent(value = "¡Bienvenido!")
            HeaderTextComponent(value = "Logueate")
            Spacer(modifier = Modifier.height(20.dp))
            textField(labelValue = "Email") {
                email = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(labelValue = "Contraseña") {
                password = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            ClickableTextComponentRegistro(navController)
            AceptarLogin(scope, email, password, auth, navController)
        }
    }
}

@Composable
fun AceptarLogin(
    scope: CoroutineScope,
    email: String,
    password: String,
    auth: AuthManager,
    navController: NavController
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(onClick = {
            scope.launch {
                LogearUsuario(context, email, password, auth, navController)
            }
        }
        ) {
            Text(text = "Haz login")
        }
    }
}

private suspend fun LogearUsuario(
    context: Context,
    email: String,
    password: String,
    auth: AuthManager,
    navController: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        when (val result = auth.signInWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                Toast.makeText(context, "Login exitoso", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
                navController.navigate("com.example.proyectoilerna.screens.Pedidos.PedidoScreen")
            }

            is AuthRes.Error -> {
                Toast.makeText(
                    context,
                    "Error al registrar:  ${result.errorMesage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    } else {
        Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
    }
}

