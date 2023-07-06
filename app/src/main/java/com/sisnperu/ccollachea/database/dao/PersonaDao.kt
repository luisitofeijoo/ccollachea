package com.sisnperu.ccollachea.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sisnperu.ccollachea.database.entities.PersonaEntity

@Dao
interface PersonaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersona(persona: PersonaEntity)

    @Query("SELECT * FROM persona_table ORDER BY id DESC")
    suspend fun getAllPersonas(): List<PersonaEntity>

    @Query("SELECT * FROM persona_table WHERE dni = :dni")
    fun buscarPorDni(dni: String): PersonaEntity?

    @Query("DELETE FROM persona_table")
    fun eliminarRegistros()
}