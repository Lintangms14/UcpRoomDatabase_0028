package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.RsDatabase
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.localRepositoryDokter

interface InterfaceDokterApp {
    val repositoryDokter: RepositoryDokter
}

class DokterApp(private val context: Context) : InterfaceDokterApp {
    override val repositoryDokter: RepositoryDokter by lazy {
        localRepositoryDokter(RsDatabase.getDatabase(context).DokterDao())
    }
}