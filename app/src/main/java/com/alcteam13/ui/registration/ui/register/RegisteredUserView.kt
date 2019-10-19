package com.alcteam13.ui.registration.ui.register

/**
 * User details post authentication that is exposed to the UI
 */
data class RegisteredUserView(
    val displayName: String,
    val user_id: String
    //... other data fields that may be accessible to the UI
)
