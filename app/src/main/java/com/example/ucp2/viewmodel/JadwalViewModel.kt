package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
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

