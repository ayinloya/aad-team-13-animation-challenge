package com.alcteam13.ui.inventory.inventory

/**
 * User details post authentication that is exposed to the UI
 */
data class InventoryView(
    val name: String,
    val inventory_id: String,
    val price: String,
    val quantity: String,
    val description: String
)
