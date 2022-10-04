package com.xr6software.eldarwallet.model

/**
 * This class is Singleton pattern to be able to use the same instance of the user object created
 * in the loginActivity across the whole App.
 */
class UserSingleton(

    var userName: String?,
    var password: String?,
    var balance: Double?,
    var cards: String?
) {

    init {
        instance = this
    }

    companion object {

        private var instance: UserSingleton? = null

        /**
         * Sets a user in the singleton instance
         * @param user User object casted to userSingleton
         */
        fun setUser(user: UserSingleton) {
            this.instance = user
        }

        /**
         * Returns a UserSingleton instance, if there's no instance creates a new user.
         * @return UserSingleton
         */
        fun getUser(): UserSingleton {
            if (this.instance == null) {
                println("instance is null")
                println("instance is null")
                println("instance is null")
                this.instance = User("", "password", 0.00, "creditCards").toUserSingletonModel()
            } else {
                println("instance is ${instance!!.userName}")
                println("instance is ${instance!!.userName}")
                println("instance is ${instance!!.userName}")
            }
            return instance as UserSingleton
        }

    }

}