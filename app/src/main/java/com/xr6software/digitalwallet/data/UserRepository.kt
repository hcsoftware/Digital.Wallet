package com.xr6software.digitalwallet.data

import android.content.Context
import com.xr6software.digitalwallet.model.User
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is a repository that interacts with the viewModel and the Room DB.
 */
@InstallIn(SingletonComponent::class)
@Module
class UserRepository @Inject constructor(@ApplicationContext val context: Context) {

    private val userDatabase = UserDatabase.getDatabase(context)

    /** This method inserts a new User in the Repo. with default balance and cards.
     * @param username String
     * @param password String
     */
    fun insertUser(username: String, password: String) {

        CoroutineScope(Dispatchers.IO).launch {
            userDatabase.userDao().insertUser(
                User(
                    username = username,
                    pass = password,
                    balance = 5000.00,
                    cards = "4580067664664646312345678978542"
                )
            )
        }
    }

    /** This method gets a User object from the repo with the correct username and password.
     * @param username String
     * @param password String
     * @return user User
     */
    suspend fun getUser(username: String, password: String): User? =
        coroutineScope {
            userDatabase.userDao().getUserFromDatabase(username, password)
        }


    /** This method validate if username exists in Repo.
     * @param username String
     * @return boolean true if user is in Repo.
     */
    suspend fun validateUser(username: String): Boolean = coroutineScope {
        userDatabase.userDao().findByName(username) != null
    }

    /** This method updates the User object in Repo.
     * @param user User
     */
    suspend fun updateUser(user: User?) {
        coroutineScope {
            userDatabase.userDao().updateUser(user!!)
        }
    }


}