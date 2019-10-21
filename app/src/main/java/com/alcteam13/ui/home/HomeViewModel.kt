package com.alcteam13.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcteam13.R
import com.alcteam13.models.Inventory
import com.alcteam13.models.Result
import com.alcteam13.ui.inventory.data.InventoryRepository
import com.alcteam13.ui.inventory.inventory.InventoryResult

class HomeViewModel(private val inventoryRepository: InventoryRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _inventoryList = MutableLiveData<InventoryResult<MutableList<Inventory>>>().apply{
        allInventory()
    }
    val inventoryList: LiveData<InventoryResult<MutableList<Inventory>>> = _inventoryList

    private fun allInventory(): LiveData<InventoryResult<MutableList<Inventory>>> {
        inventoryRepository.getAll { result ->
            if (result is Result.Success) {
                _inventoryList.value = InventoryResult(success = result.data)
            } else {
                _inventoryList.value = InventoryResult(error = R.string.saving_failed)
            }
        }

        return  inventoryList

    }


}