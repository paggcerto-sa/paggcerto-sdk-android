package br.com.paggcerto.pagcertosdk.model.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin")
data class Bin(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "card_brand")
    val cardBrand: String,
    @ColumnInfo(name = "regex")
    val regex: String,
    @ColumnInfo(name = "debit")
    val debit: Boolean,
    @ColumnInfo(name = "emv_supported")
    val emvSupported: Boolean,
    @ColumnInfo(name = "maximum_installment")
    val maximumInstallment: Int
)