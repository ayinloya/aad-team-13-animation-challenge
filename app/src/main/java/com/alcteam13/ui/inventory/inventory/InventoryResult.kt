package com.alcteam13.ui.inventory.inventory

/**
 * Authentication result : success (user details) or error message.
 */
data class InventoryResult(
    val success: InventoryView? = null,
    val error: Int? = null
)
