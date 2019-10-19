package com.alcteam13.ui.registration.data

import android.util.Log
import com.alcteam13.models.Result
import com.alcteam13.models.User
import com.alcteam13.utils.IVConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class RegisterDataSource {

    fun register(email: String, password: String, callback: (Result<User>) -> Unit) {
        try {
            var user = User()
            var status = false
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    status = task.isSuccessful
                    Log.d("This runs?2: ", "${task.isSuccessful}")

                    if (task.isSuccessful) {
                        user = User(
                            FirebaseAuth.getInstance().currentUser!!.uid,
                            email.substring(0, email.indexOf("@")), "", "1"
                        )
                        callback(Result.Success(user))

                        FirebaseDatabase.getInstance().reference
                            .child(IVConstants.USERS_DB_NODE)
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user)
                    } else {
                        callback(Result.Error(IOException("Error registering in")))
                    }
                }
            Log.d("Check status", "$status")
//            if (status) {
//                Log.d("This runs?","$user")
//                return Result.Success(user)
//            }
//
//            return Result.Error(IOException("Error registering in"))
        } catch (e: Throwable) {
            callback(Result.Error(IOException("Error registering in", e)))
        }
    }

    fun delete() {
        // TODO: revoke authentication
    }
}

