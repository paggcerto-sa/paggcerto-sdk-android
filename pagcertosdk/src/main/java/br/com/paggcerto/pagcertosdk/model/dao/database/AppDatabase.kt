package br.com.paggcerto.pagcertosdk.model.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.paggcerto.pagcertosdk.model.dao.Bin
import br.com.paggcerto.pagcertosdk.model.dao.BinDao

@Database(entities = [Bin::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun binDao(): BinDao
}