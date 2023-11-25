package com.example.proyectoilerna.screens.Registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoilerna.data.AuthRepository
import com.example.proyectoilerna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _registroState = Channel<RegistroState>()
    val registroState = _registroState.receiveAsFlow()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _registroState.send(RegistroState(isSuccess = "Sign In Success "))
                }

                is Resource.Loading -> {
                    _registroState.send(RegistroState(isLoading = true))
                }

                is Resource.Error -> {
                    _registroState.send(RegistroState(isError = result.messagge))
                }
            }

        }
    }
}