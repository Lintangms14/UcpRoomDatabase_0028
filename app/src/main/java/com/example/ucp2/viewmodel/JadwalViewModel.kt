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

data class JadwalUiState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJadwal = FormErrorStateJadwal(),
    val snackBarMessage: String? = null,
)

class JadwalViewModel(
    private val repositoryJadwal: RepositoryJadwal
) : ViewModel(){

    var uiState by mutableStateOf(JadwalUiState())

    fun updateState(jadwalEvent: JadwalEvent){
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent,
        )

    }
    private  fun validateFields() : Boolean{
        val event = uiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            id = if (event.id.isNotEmpty()) "Id tidak boleh kosong" else null,
            namaPasien = if (event.namaPasien.isNotEmpty()) "Nama Pasien tidak boleh kosong" else null,
            namaDokter = if (event.namaDokter.isNotEmpty()) "Nama Dokter tidak boleh kosong" else null,
            noTelpon = if (event.noTelpon.isNotEmpty()) "noTelepon tidak boleh kosong" else null,
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) "Tanggal Konsultasi tidak boleh kosong" else null,
            status = if (event.status.isNotEmpty()) "Status tidak boleh kosong" else null
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {

        val currentEvent = uiState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.insertJadwal(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJadwal()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        }else {
            uiState = uiState.copy(
                snackBarMessage = "Data tidak valid. Periksa kembali data Anda."
            )
        }
    }
    fun resetSnackBarMessage() {
        uiState = uiState.copy(
            snackBarMessage = null
        )
    }
}