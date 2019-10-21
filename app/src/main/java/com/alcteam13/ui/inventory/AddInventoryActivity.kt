package com.alcteam13.ui.inventory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alcteam13.R
import com.alcteam13.ui.inventory.inventory.InventoryView
import com.alcteam13.ui.inventory.inventory.InventoryViewModel
import com.alcteam13.ui.inventory.inventory.InventoryViewModelFactory
import com.alcteam13.ui.registration.ui.register.afterTextChanged
import kotlinx.android.synthetic.main.content_add_inventory.*


class AddInventoryActivity : AppCompatActivity() {

    private lateinit var inventoryViewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventory)

        inventoryViewModel = ViewModelProviders.of(this, InventoryViewModelFactory())
            .get(InventoryViewModel::class.java)


        inventoryViewModel.registerFormState.observe(this, Observer {
            val registerState = it ?: return@Observer

            // disable register button unless email / password  and confirm password are valid
            save.isEnabled = registerState.isDataValid

            if (registerState.nameError != null) {
                name.error = getString(registerState.nameError)
            }
            if (registerState.priceError != null) {
                price.error = getString(registerState.priceError)
            }
            if (registerState.quantityError != null) {
                quantity.error = getString(registerState.quantityError)
            }
        })

        name.afterTextChanged {
            formDataChanged()
        }
        price.afterTextChanged {
            formDataChanged()
        }
        quantity.afterTextChanged {
            formDataChanged()
        }


        save.setOnClickListener {
            Log.d("save click", "initial")
            inventoryViewModel.create(
                name.text.toString(),
                price.text.toString(),
                quantity.text.toString(),
                "",
                description.text.toString()
            ).observe(this, Observer {
                if (it.success is InventoryView) {
                    Toast.makeText(this, "${it.success.name} Saved", Toast.LENGTH_LONG).show()
                    var returnIntent = Intent()
                    returnIntent.putExtra("inventory", it.success)

                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error saving ${resources.getString(it.error!!)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun formDataChanged() {
        inventoryViewModel.registerDataChanged(
            name.text.toString(),
            price.text.toString(),
            quantity.text.toString(),
            "",
            description.toString()
        )
    }

}
