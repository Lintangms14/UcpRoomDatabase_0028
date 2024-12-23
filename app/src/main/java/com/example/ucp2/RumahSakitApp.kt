package com.example.ucp2

import android.app.Application
import com.example.ucp2.dependenciesinjection.DokterApp
import com.example.ucp2.dependenciesinjection.JadwalApp

class RumahSakitApp : Application() {
    lateinit var dokterApp: DokterApp
    lateinit var jadwalApp: JadwalApp

    override fun onCreate() {
        super.onCreate()

        dokterApp = DokterApp(this)
        jadwalApp = JadwalApp(this)
    }
}
