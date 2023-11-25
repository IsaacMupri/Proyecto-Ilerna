package com.example.proyectoilerna.util

sealed class Resource<T>(val data: T? = null, val messagge: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(messagge: String?, data: T? = null) : Resource<T>(data, messagge)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
