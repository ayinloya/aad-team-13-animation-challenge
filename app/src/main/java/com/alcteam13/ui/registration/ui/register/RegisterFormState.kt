package com.alcteam13.ui.registration.ui.register

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val passwordConfirmError: Int? = null,
    val isDataValid: Boolean = false
)
