package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal
import kotlinx.coroutines.launch

data class JadwalEvent(
    val id : String = "",
    val namaDokter : String = "",
    val namaPasien : String = "",
    val noTelpon : String = "",
    val tanggalKonsultasi : String = "",
    val status : String = ""
)

fun JadwalEvent.toJadwalEntity(): Jadwal  = Jadwal(
    id = id,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noTelpon = noTelpon,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)

data class FormErrorStateJadwal(
    val id: String? = null,
    val namaPasien: String? = null,
    val namaDokter: String? = null,
    val noTelpon: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null
) {
    fun isValid(): Boolean {
        return id != null && namaPasien != null && namaDokter != null &&
                noTelpon != null && tanggalKonsultasi != null && status != null

    }
}

