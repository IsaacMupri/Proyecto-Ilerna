package com.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.components.ClickableTextComponentRegistro
import com.components.HeaderTextComponent
import com.components.NormalTextComponent
import com.components.PasswordTextField
import com.components.buttonComponent
import com.components.textField
import com.example.proyectoilerna.screens.Login.LoginViewModel

@Composable
fun Login(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
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
            textField(labelValue = "Email")
            Spacer(modifier = Modifier.height(20.dp))
            PasswordTextField(labelValue = "Contraseña")
            Spacer(modifier = Modifier.height(20.dp))
            ClickableTextComponentRegistro(navController)
            AceptarLogin(viewModel)
        }
    }
}

@Composable
fun AceptarLogin(viewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        buttonComponent("Aceptar")
    }
}

