package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal

@Database(entities = [Dokter::class], version = 1, exportSchema = false)
abstract class DokterDatabase : RoomDatabase() {
    abstract fun dokterDao(): DokterDao

    companion object {
        @Volatile
        private var Instance: DokterDatabase? = null

        fun getDatabase(context: Context): DokterDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DokterDatabase::class.java,
                    "dokterDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
