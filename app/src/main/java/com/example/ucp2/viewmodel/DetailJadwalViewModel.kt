package com.example.ucp2.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.ui.navigation.AlamatNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetailJadwalUiState(
    val detailUiEvent: JadwalEvent = JadwalEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get()= detailUiEvent == JadwalEvent()
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != JadwalEvent()
}
fun Jadwal.toDetailJadwalUiEvent(): JadwalEvent {
    return JadwalEvent(
        id = id,
        namaPasien = namaPasien,
        namaDokter = namaDokter,
        noTelpon = noTelpon,
        tanggalKonsultasi = tanggalKonsultasi,
        status = status
    )
}

class DetailJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJadwal,

    ) : ViewModel() {
    private val _id: String = checkNotNull(savedStateHandle[AlamatNavigation.DestinasiDetail.id])

    val detailUiState: StateFlow<DetailJadwalUiState> = repositoryJadwal.getJadwal(_id)
        .filterNotNull()
        .map {
            DetailJadwalUiState(
                detailUiEvent = it.toDetailJadwalUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailJadwalUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailJadwalUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailJadwalUiState(
                isLoading = true
            ),
        )
    fun deleteJadwal() {
        detailUiState.value.detailUiEvent.toJadwalEntity().let {
            viewModelScope.launch {
                repositoryJadwal.deleteJadwal(it)
            }
        }
    }
}