package com.xr6software.digitalwallet.model

/**
 * UserSingleton model
 * Init in loginActivity to use in App.
 */
class UserSingleton(

    var userName: String,
    var password: String,
    var balance: Double,
    var cards: String,
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
                this.instance = User("", "password", 0.00, "creditCards").toUserSingletonModel()
            } else {
            }
            return instance as UserSingleton
        }

    }

}

