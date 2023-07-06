package com.sisnperu.ccollachea.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persona_table")
data class PersonaEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("dni") val dni: String,
    @ColumnInfo("nombres") val nombres: String
)