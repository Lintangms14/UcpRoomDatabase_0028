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

data class DokterUiState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorStateDokter = FormErrorStateDokter(),
    val snackBarMessage: String? = null,
)

class DokterViewModel(
    private val repositoryDokter: RepositoryDokter
) : ViewModel(){

    var uiState by mutableStateOf(DokterUiState())

    fun updateState(dokterEvent: DokterEvent){
        uiState = uiState.copy(
            dokterEvent = dokterEvent,
        )

    }
    private  fun validateFields() : Boolean{
        val event = uiState.dokterEvent
        val errorState = FormErrorStateDokter(
            id = if (event.id.isNotEmpty()) "Id tidak boleh kosong" else null,
            nama = if (event.nama.isNotEmpty()) "Nama tidak boleh kosong" else null,
            spesialis = if (event.spesialis.isNotEmpty()) "Spesialis tidak boleh kosong" else null,
            klinik = if (event.klinik.isNotEmpty()) "Klinik tidak boleh kosong" else null,
            NoTelpon = if (event.NoTelepon.isNotEmpty()) "No Telepon tidak boleh kosong" else null,
            jamKerja = if (event.jamKerja.isNotEmpty()) "Jam Kerja tidak boleh kosong" else null
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {

        val currentEvent = uiState.dokterEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDokter.insertDokter(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dokterEvent = DokterEvent(),
                        isEntryValid = FormErrorStateDokter()
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