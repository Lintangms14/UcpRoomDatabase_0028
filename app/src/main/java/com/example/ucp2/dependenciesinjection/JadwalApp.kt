package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.RsDatabase
import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.repository.localRepositoryJadwal

interface InterfaceJadwalApp {
    val repositoryJadwal: RepositoryJadwal
}

class JadwalApp(private val context: Context) : InterfaceJadwalApp {
    override val repositoryJadwal: RepositoryJadwal by lazy {
        localRepositoryJadwal(RsDatabase.getDatabase(context).JadwalDao())
    }
}
