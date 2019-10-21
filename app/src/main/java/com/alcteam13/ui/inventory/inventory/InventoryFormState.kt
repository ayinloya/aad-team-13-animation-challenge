package com.alcteam13.ui.inventory.inventory

/**
 * Data validation state of the login form.
 */
data class InventoryFormState(
    val nameError: Int? = null,
    val priceError: Int? = null,
    val quantityError: Int? = null,
    val isDataValid: Boolean = false
)
