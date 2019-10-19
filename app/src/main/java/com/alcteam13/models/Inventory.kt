package com.alcteam13.models

class Inventory(
    var name: String,
    val image: String,
    val description: String,
    val price: String,
    val quantity: String,
    val inventory_id: String
) {

    constructor() : this("", "", "", "", "", "")


}