package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dokter")
data class Dokter(
    @PrimaryKey
    val id: String,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val NoTelpon: String,
    val jamKerja: String,
    val warnaSpesialis: Int
)