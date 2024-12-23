package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDokter
import kotlinx.coroutines.launch

data class DokterEvent(
    val id : String = "",
    val nama : String = "",
    val spesialis : String = "",
    val klinik : String = "",
    val NoTelepon : String = "",
    val jamKerja : String = ""
)

fun DokterEvent.toDokterEntity(): Dokter  = Dokter(
    id = id,
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    NoTelpon = NoTelepon,
    jamKerja = jamKerja
)


data class FormErrorStateDokter(
    val id: String? = null,
    val nama: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val NoTelpon: String? = null,
    val jamKerja: String? = null
) {
    fun isValid(): Boolean {
        return id != null && nama != null && spesialis != null &&
                klinik != null && NoTelpon != null && jamKerja != null

    }
}

