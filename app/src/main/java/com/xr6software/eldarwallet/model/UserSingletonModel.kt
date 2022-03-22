package com.xr6software.eldarwallet.model

import android.util.Log


class UserSingletonModel(user: User) {

    companion object {

        lateinit var username : String
        lateinit var password : String
        var balance : Double = 0.0
        lateinit var cards : String
        private var INSTANCE: UserSingletonModel? = null

        fun getUser(): UserSingletonModel {
            if (UserSingletonModel.INSTANCE == null) {
                UserSingletonModel(User("vacio","vacio", 0.00,"vacio"))
            }
            return UserSingletonModel.INSTANCE!!

        }

    }

    init {

        username = user.username.toString()
        password = user.pass.toString()
        balance = user.balance!!.toDouble()
        cards = user.cards!!.toString()

    }


}