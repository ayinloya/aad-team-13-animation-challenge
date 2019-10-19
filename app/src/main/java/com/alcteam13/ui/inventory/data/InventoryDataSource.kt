package com.alcteam13.ui.inventory.data

import com.alcteam13.models.Inventory
import com.alcteam13.models.Result
import com.alcteam13.utils.IVConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class InventoryDataSource {

    fun create(
        name: String,
        price: String,
        quantity: String,
        image: String,
        description: String,
        callback: (Result<Inventory>) -> Unit
    ) {
        try {
            val inventory = Inventory()
            FirebaseDatabase.getInstance().reference
                .child(IVConstants.INVENTORY_DB_NODE)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(inventory)
        } catch (e: Throwable) {
            callback(Result.Error(IOException("Error saving inventory", e)))
        }
    }

    fun delete(id: String, callback: (Result<Inventory>) -> Unit) {
        try {
            val inventory = Inventory()
            FirebaseDatabase.getInstance().reference
                .child(IVConstants.INVENTORY_DB_NODE)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)

        } catch (e: Throwable) {
            callback(Result.Error(IOException("Error saving inventory", e)))
        }
    }
}

