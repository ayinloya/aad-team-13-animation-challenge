package com.alcteam13.ui.inventory.data

import android.util.Log
import com.alcteam13.models.Inventory
import com.alcteam13.models.Result
import com.alcteam13.utils.IVConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class InventoryDataSource {
    private val mDatabase by lazy {
        FirebaseDatabase.getInstance().getReference(IVConstants.INVENTORY_DB_NODE)
    }

    fun create(
        name: String,
        price: String,
        quantity: String,
        image: String,
        description: String,
        callback: (Result<Inventory>) -> Unit
    ) {
        try {
            val inventory = Inventory(name, "", description, price, quantity)
            inventory.inventory_id = mDatabase.push().key!!
            inventory.user_id = FirebaseAuth.getInstance().currentUser!!.uid
            Log.d(
                "Datasource",
                "Data $inventory --- ${FirebaseAuth.getInstance().currentUser!!.uid}"
            )

            mDatabase
                .child(inventory.inventory_id)
                .setValue(inventory).addOnCompleteListener {
                    Log.d("Datasource", "Data result")
                    if (it.isSuccessful) {
                        callback(Result.Success(inventory))
                    }
                }

        } catch (e: Throwable) {
            callback(Result.Error(IOException("Error saving inventory", e)))
        }
    }

    fun all(callback: (Result<MutableList<Inventory>>) -> Unit) {
        try {
            val inventoryList: MutableList<Inventory> = mutableListOf()
            FirebaseDatabase.getInstance().getReference(IVConstants.INVENTORY_DB_NODE)
                .orderByValue().addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        inventoryList.clear()
                        for (child: DataSnapshot in dataSnapshot.children) {
                            val inventoryParsed: Inventory = child.getValue(Inventory::class.java)!!
                            inventoryList.add(inventoryParsed)
                        }
                        callback(Result.Success(inventoryList))
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })

        } catch (e: Throwable) {
            callback(Result.Error(IOException("Error saving inventory", e)))
        }
    }

    fun delete(id: String, callback: (Result<Inventory>) -> Unit) {
        try {
            val inventory = Inventory()
//            mDatabase
//                .child(IVConstants.INVENTORY_DB_NODE)
//                .child(FirebaseAuth.getInstance().currentUser!!.uid)
//                .orderByKey().addChildEventListener()

        } catch (e: Throwable) {
            callback(Result.Error(IOException("Error saving inventory", e)))
        }
    }

    val childEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            val inventory = dataSnapshot.getValue(Inventory::class.java)
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            val newInventory = dataSnapshot.getValue(Inventory::class.java)
            val inventoryKey = dataSnapshot.key
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            val commentKey = dataSnapshot.key
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    }

}

