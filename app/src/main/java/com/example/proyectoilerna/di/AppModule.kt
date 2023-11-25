package com.example.proyectoilerna.di

import com.example.proyectoilerna.repositories.auth_repoImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFireBaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideRespositoryImpl(firebaseAuth: FirebaseAuth): auth_repoImpl {
        return auth_repoImpl(firebaseAuth)
    }
}