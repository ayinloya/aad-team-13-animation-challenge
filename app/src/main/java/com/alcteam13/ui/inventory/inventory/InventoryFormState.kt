package com.alcteam13.ui.inventory.inventory

/**
 * Data validation state of the login form.
 */
data class InventoryFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val passwordConfirmError: Int? = null,
    val isDataValid: Boolean = false
)
