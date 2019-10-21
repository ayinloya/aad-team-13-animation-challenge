package com.alcteam13.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alcteam13.ui.inventory.data.InventoryDataSource
import com.alcteam13.ui.inventory.data.InventoryRepository
import com.alcteam13.ui.inventory.inventory.InventoryViewModel

/**
 * ViewModel provider factory to instantiate InventoryViewModel.
 * Required given InventoryViewModel has a non-empty constructor
 */
class HomeViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                inventoryRepository = InventoryRepository(
                    dataSource = InventoryDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
