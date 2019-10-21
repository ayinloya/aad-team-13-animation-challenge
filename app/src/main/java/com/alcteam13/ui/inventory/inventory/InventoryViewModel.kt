package com.alcteam13.ui.inventory.inventory

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcteam13.R
import com.alcteam13.models.Inventory
import com.alcteam13.models.Result
import com.alcteam13.ui.inventory.data.InventoryRepository


class InventoryViewModel(private val inventoryRepository: InventoryRepository) : ViewModel() {

    private val _inventoryForm = MutableLiveData<InventoryFormState>()
    val registerFormState: LiveData<InventoryFormState> = _inventoryForm

    private val _inventoryResult = MutableLiveData<InventoryResult<InventoryView>>()
    val registerResult: LiveData<InventoryResult<InventoryView>> = _inventoryResult

    fun create(
        name: String,
        price: String,
        quantity: String,
        image: String,
        description: String
    ): MutableLiveData<InventoryResult<InventoryView>> {
        // can be launched in a separate asynchronous job
        inventoryRepository.create(name, price, quantity, image, description) { result ->
            Log.d("repo","Create in repo $result")
            if (result is Result.Success) {
                _inventoryResult.value =
                    InventoryResult(
                        success = InventoryView(
                            name = result.data.name,
                            description = result.data.description,
                            inventory_id = result.data.inventory_id,
                            price = result.data.price,
                            quantity = result.data.quantity
                        )
                    )
            } else {
                Log.d("R.string.saving_failed","${R.string.saving_failed}")
                _inventoryResult.value = InventoryResult(error = R.string.saving_failed)
            }

        }

        return _inventoryResult


    }

    fun registerDataChanged(
        name: String,
        price: String,
        quantity: String,
        image: String,
        description: String) {
        if (name.isBlank()) {
            _inventoryForm.value = InventoryFormState(nameError = R.string.invalid_inventory_name)
        } else if (quantity.isBlank()) {
            _inventoryForm.value = InventoryFormState(quantityError = R.string.invalid_quantity)
        } else if (price.isBlank()) {
            _inventoryForm.value = InventoryFormState(priceError = R.string.invalid_price)
        } else {
            _inventoryForm.value = InventoryFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
