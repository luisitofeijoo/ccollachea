package com.sisnperu.ccollachea.network

// UsuariosApiService.kt
import com.sisnperu.ccollachea.model.Persona
import retrofit2.http.GET

interface PersonasApiService{
    @GET("api/personas")
    suspend fun obtenerPersonas(): List<Persona>
}