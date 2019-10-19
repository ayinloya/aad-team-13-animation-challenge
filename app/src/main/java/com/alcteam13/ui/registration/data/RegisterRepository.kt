package com.alcteam13.ui.registration.data

import com.alcteam13.models.Result
import com.alcteam13.models.User


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class RegisterRepository(val dataSource: RegisterDataSource) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isRegisteredIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun delete() {
        user = null
        dataSource.delete()
    }

    fun register(email: String, password: String,callback: (Result<User>) -> Unit) {
        // handle registration
        dataSource.register(email, password) { result ->

            if (result is Result.Success) {
                setRegisteredUser(result.data)
            }

            callback(result)
        }

    }

    private fun setRegisteredUser(user: User) {
        this.user = user
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
