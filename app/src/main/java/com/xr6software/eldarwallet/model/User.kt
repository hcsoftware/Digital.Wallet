package com.xr6software.eldarwallet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "pass") val pass: String?,
    @ColumnInfo(name = "balance") var balance: Double?,
    @ColumnInfo(name = "credit_cards") var cards: String?
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}