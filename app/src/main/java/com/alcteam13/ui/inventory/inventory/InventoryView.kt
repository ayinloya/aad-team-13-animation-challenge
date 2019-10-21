package com.alcteam13.ui.inventory.inventory

import android.os.Parcel
import android.os.Parcelable


data class InventoryView( val name: String,
                          val inventory_id: String,
                          val price: String,
                          val quantity: String,
                          val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InventoryView> {
        override fun createFromParcel(parcel: Parcel): InventoryView {
            return InventoryView(parcel)
        }

        override fun newArray(size: Int): Array<InventoryView?> {
            return arrayOfNulls(size)
        }
    }

}
