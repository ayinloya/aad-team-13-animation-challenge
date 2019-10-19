package com.alcteam13.ui.inventory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alcteam13.R
import com.alcteam13.models.Result
import com.alcteam13.ui.inventory.inventory.InventoryViewModelFactory
import com.alcteam13.ui.inventory.inventory.InventoryViewModel
import kotlinx.android.synthetic.main.content_add_inventory.*

class AddInventoryActivity : AppCompatActivity() {

    private lateinit var inventoryViewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventory)

        inventoryViewModel = ViewModelProviders.of(this, InventoryViewModelFactory())
            .get(InventoryViewModel::class.java)

        save.setOnClickListener {
            inventoryViewModel.create(
                name.text.toString(),
                price.text.toString(),
                quantity.text.toString(),
                "",
                description.text.toString()
            ) {
                if (it is Result.Success) {
                    Toast.makeText(this, "$name Saved", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

}
