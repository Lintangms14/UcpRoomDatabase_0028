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

