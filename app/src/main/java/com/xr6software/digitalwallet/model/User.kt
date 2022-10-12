package com.xr6software.digitalwallet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User Entity For Room Database.
 */
@Entity
data class User(
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "pass") val pass: String,
    @ColumnInfo(name = "balance") var balance: Double,
    @ColumnInfo(name = "credit_cards") var cards: String
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

/**
 * Converts user entity to UserSingleton model to use in App.
 */
fun User.toUserSingletonModel() = UserSingleton(
    userName = this.username,
    password = this.pass,
    balance = this.balance,
    cards = this.cards
)
