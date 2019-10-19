package com.alcteam13.ui.inventory.inventory

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

    private val _inventoryResult = MutableLiveData<InventoryResult>()
    val registerResult: LiveData<InventoryResult> = _inventoryResult

    fun create(
        name: String,
        price: String,
        quantity: String,
        image: String,
        description: String,
        callback: (Result<Inventory>) -> Unit
    ) {
        // can be launched in a separate asynchronous job
        inventoryRepository.create(name, price, quantity, image, description) { result ->
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
                _inventoryResult.value = InventoryResult(error = R.string.saving_failed)
            }
        }


    }

    fun registerDataChanged(username: String, password: String, confirm_password: String) {
        if (!isUserNameValid(username)) {
            _inventoryForm.value = InventoryFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _inventoryForm.value = InventoryFormState(passwordError = R.string.invalid_password)
        } else if (password != confirm_password) {
            _inventoryForm.value =
                InventoryFormState(passwordConfirmError = R.string.invalid_password_not_matched)
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
