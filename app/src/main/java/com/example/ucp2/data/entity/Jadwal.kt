package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jadwal")
data class Jadwal(
    @PrimaryKey
    val id: String,
    val namaDokter: String,
    val namaPasien: String,
    val noTelpon: String,
    val tanggalKonsultasi: String,
    val status: String
)