package com.example.ucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.ui.navigation.AlamatNavigation
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJadwal
) : ViewModel() {

    var updateJadwalUiState by mutableStateOf(JadwalUiState())
        private set

    private val _id: String = checkNotNull(savedStateHandle[AlamatNavigation.DestinasiEdit.id])

    init {
        viewModelScope.launch {
            updateJadwalUiState = repositoryJadwal.getJadwal(_id)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        updateJadwalUiState = updateJadwalUiState.copy(
            jadwalEvent = jadwalEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateJadwalUiState.jadwalEvent
        val errorState = FormErrorStateJadwal(
            id = if (event.id.isNotEmpty()) null else "Id Tidak Boleh Kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien Tidak Boleh Kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter Tidak Boleh Kosong",
            noTelpon = if (event.noTelpon.isNotEmpty()) null else "No Telepon Tidak Boleh Kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi Tidak Boleh Kosong",
            status = if (event.status.isNotEmpty()) null else "Status Tidak Boleh Kosong",
        )

        updateJadwalUiState = updateJadwalUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateJadwalUiState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.updateJadwal(currentEvent.toJadwalEntity())
                    updateJadwalUiState = updateJadwalUiState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJadwal(),
                    )
                    println("snackBarMessage Diatur: ${updateJadwalUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateJadwalUiState = updateJadwalUiState.copy(
                        snackBarMessage = "Data Gagal Diupdate"
                    )
                }
            }
        } else  {
            updateJadwalUiState = updateJadwalUiState.copy(
                snackBarMessage = "Data Gagal Diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateJadwalUiState = updateJadwalUiState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUIStateMhs(): JadwalUiState = JadwalUiState(
    jadwalEvent = this.toDetailJadwalUiEvent(),
)