package com.xr6software.eldarwallet.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE username LIKE :first LIMIT 1")
    fun findByName(first: String): User

    @Query("UPDATE user SET credit_cards = :creditCards WHERE username = :first")
    fun addCreditCardByName(first: String, creditCards : String)

    @Insert
    fun insertAll(vararg users: User)

    @Insert
    fun insertUser(user : User)

    @Delete
    fun delete(user: User)
}