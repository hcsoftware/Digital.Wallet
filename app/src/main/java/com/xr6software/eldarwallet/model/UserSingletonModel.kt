package com.xr6software.eldarwallet.model

import android.util.Log


class UserSingletonModel() {

    companion object {

        lateinit var userSingleton : User

        private var INSTANCE: UserSingletonModel? = null

        fun setUser(user : User) {
            this.userSingleton = user
        }

        fun getUser(): User {
            if (UserSingletonModel.userSingleton == null) {
                UserSingletonModel.userSingleton = User("","password", 0.00, "creditCards")
            }
            return UserSingletonModel.userSingleton
        }

    }

}