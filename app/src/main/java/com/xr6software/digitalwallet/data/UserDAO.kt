package com.xr6software.digitalwallet.data

import androidx.room.*
import com.xr6software.digitalwallet.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE username LIKE :first LIMIT 1")
    fun findByName(first: String): User

    @Query("SELECT * FROM user WHERE username LIKE :user AND pass LIKE :pass LIMIT 1")
    fun getUserFromDatabase(user: String, pass: String): User

    @Query("UPDATE user SET credit_cards = :creditCards WHERE username = :first")
    fun addCreditCardByName(first: String, creditCards : String)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Update
    fun updateUser(user: User)

    @Insert
    fun insertAll(vararg users: User)

    @Insert
    fun insertUser(user : User)

    @Delete
    fun delete(user: User)
}