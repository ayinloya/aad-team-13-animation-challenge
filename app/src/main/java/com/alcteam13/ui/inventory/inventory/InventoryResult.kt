package com.alcteam13.ui.inventory.inventory

/**
 * Authentication result : success (user details) or error message.
 */
data class InventoryResult<out T : Any>(
    val success: T? = null,
    val error: Int? = null
)
