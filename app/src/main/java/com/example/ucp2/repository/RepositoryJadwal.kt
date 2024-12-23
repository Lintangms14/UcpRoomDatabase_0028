package com.example.ucp2.repository

import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJadwal {
    suspend fun insertJadwal(jadwal: Jadwal)

    fun getAllJadwal(): Flow<List<Jadwal>>

    fun getJadwal(id: String): Flow<Jadwal>

    suspend fun updateJadwal(jadwal: Jadwal)

    suspend fun deleteJadwal(jadwal: Jadwal)
}
