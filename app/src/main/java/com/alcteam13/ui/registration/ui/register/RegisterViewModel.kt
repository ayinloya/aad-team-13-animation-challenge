package com.alcteam13.ui.registration.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcteam13.R
import com.alcteam13.models.Result
import com.alcteam13.ui.registration.data.RegisterRepository


class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _loginForm

    private val _loginResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _loginResult

    fun register(email: String, password: String) {
        // can be launched in a separate asynchronous job
        registerRepository.register(email, password) { result ->
            if (result is Result.Success) {
                _loginResult.value =
                    RegisterResult(
                        success = RegisteredUserView(
                            displayName = result.data.name,
                            user_id = result.data.user_id
                        )
                    )
            } else {
                _loginResult.value = RegisterResult(error = R.string.registration_failed)
            }
        }


    }

    fun registerDataChanged(username: String, password: String, confirm_password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (password != confirm_password) {
            _loginForm.value =
                RegisterFormState(passwordConfirmError = R.string.invalid_password_not_matched)
        } else {
            _loginForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
