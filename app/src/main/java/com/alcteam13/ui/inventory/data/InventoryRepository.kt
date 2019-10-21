package com.alcteam13.ui.inventory.data

import com.alcteam13.models.Inventory
import com.alcteam13.models.Result


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class InventoryRepository(val dataSource: InventoryDataSource) {

    // in-memory cache of the

    var inventory: Inventory? = null
        private set

    var inventoryList: List<Inventory>? = null
        private set


    init {
        inventory = null
    }

    fun delete() {
        dataSource.delete(inventory!!.inventory_id) {
            inventory = null
        }
    }

    fun create(
        name: String,
        price: String,
        quantity: String,
        image: String,
        description: String,
        callback: (Result<Inventory>) -> Unit
    ) {
        // handle registration
        dataSource.create(name, price, quantity, image, description) { result ->

            if (result is Result.Success) {
                setInventory(result.data)
            }

            callback(result)
        }

    }


    fun getAll(callback: (Result<MutableList<Inventory>>) -> Unit) {
        // handle inventory list
        dataSource.all { result ->

            if (result is Result.Success) {
                setInventoryList(result.data)
            }

            callback(result)
        }

    }

    private fun setInventoryList(inventoryList: List<Inventory>) {
        this.inventoryList = inventoryList
    }

    private fun setInventory(inventory: Inventory) {
        this.inventory = inventory
    }
}
