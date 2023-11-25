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
import com.components.ClickableTextComponentLogin
import com.components.HeaderTextComponent
import com.components.NormalTextComponent
import com.components.PasswordTextField
import com.components.textField
import com.example.proyectoilerna.util.AuthManager
import com.example.proyectoilerna.util.AuthRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun Registro(navController: NavController, auth: AuthManager) {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            NormalTextComponent(value = "¡Bienvenido!,")
            HeaderTextComponent(value = "crea una cuenta")
            Spacer(modifier = Modifier.height(20.dp))
            textField(labelValue = "Email") {
                email = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(labelValue = "Contraseña") {
                password = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            ClickableTextComponentLogin(navController)
            AceptarRegistro(scope, email, password, auth, navController)
        }
    }
}

@Composable
fun AceptarRegistro(
    scope: CoroutineScope,
    email: String,
    password: String,
    auth: AuthManager,
    navController: NavController,
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(onClick = {
            scope.launch {
                registrarUsuario(context, email, password, auth, navController)
            }
        }
        ) {
            Text(text = "Regístrate")
        }
    }
}

private suspend fun registrarUsuario(
    context: Context,
    email: String,
    password: String,
    auth: AuthManager,
    navController: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        when (val result = auth.createUserWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
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



