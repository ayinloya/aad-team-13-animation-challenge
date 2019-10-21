package com.alcteam13.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alcteam13.R
import com.alcteam13.models.Inventory

class InventoryAdapter(val context: Context, private var items: MutableList<Inventory>) :
    RecyclerView.Adapter<InventoryAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(context).inflate(R.layout.inventory_list, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindItems(items[position])
    }

    fun setInventory(inventory: MutableList<Inventory>) {
        this.items = inventory
        notifyItemRangeChanged(0,inventory.size)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var price: TextView = itemView.findViewById(R.id.price)
        var quantity: TextView = itemView.findViewById(R.id.quantity)

        fun bindItems(inventory: Inventory) {
            name.text = inventory.name
//            name.text = inventory.description
            price.text = inventory.price
            quantity.text = inventory.quantity
        }
    }

}
