package com.alcteam13.ui.inventory.data

import com.alcteam13.models.Inventory
import com.alcteam13.models.Result


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class InventoryRepository(val dataSource: InventoryDataSource) {

    // in-memory cache of the loggedInUser object
    var inventory: Inventory? = null
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

    private fun setInventory(inventory: Inventory) {
        this.inventory = inventory
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
