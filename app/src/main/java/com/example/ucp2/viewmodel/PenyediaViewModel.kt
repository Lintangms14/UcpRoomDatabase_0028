package com.example.ucp2.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.RumahSakitApp
import com.example.ucp2.ui.view.jadwal.DetailJdwlView

object PenyediaViewModel {
    val factory = viewModelFactory {
        initializer {
            DokterViewModel(
                RumahSakitApp().dokterApp.repositoryDokter
            )
        }

        initializer {
            HomeDokterViewModel(
                RumahSakitApp().dokterApp.repositoryDokter
            )
        }

        initializer {
            JadwalViewModel(
                RumahSakitApp().jadwalApp.repositoryJadwal
            )
        }

        initializer {
            HomeJadwalViewModel(
                RumahSakitApp().jadwalApp.repositoryJadwal
            )
        }

        initializer {
            DetailJadwalViewModel(
                createSavedStateHandle(),
                RumahSakitApp().jadwalApp.repositoryJadwal
            )
        }

        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                RumahSakitApp().jadwalApp.repositoryJadwal
            )
        }
    }
}

fun CreationExtras.RumahSakitApp(): RumahSakitApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as RumahSakitApp)