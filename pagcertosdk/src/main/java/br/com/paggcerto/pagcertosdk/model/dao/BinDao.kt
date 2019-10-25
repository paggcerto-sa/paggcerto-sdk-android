package br.com.paggcerto.pagcertosdk.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BinDao {
    @get:Query("SELECT * FROM bin")
    val all: List<Bin>

    @Insert
    fun insertAll(vararg bins: Bin)
}