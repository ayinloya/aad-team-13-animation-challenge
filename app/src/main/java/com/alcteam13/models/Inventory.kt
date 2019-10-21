package com.alcteam13.models

class Inventory(
    var name: String,
    val image: String,
    val description: String,
    val price: String,
    val quantity: String
) {
    lateinit var user_id: String
    var inventory_id:String =""


    constructor() : this("", "", "", "", "")

    override fun toString(): String {
        return "Inventory(name='$name', image='$image', description='$description', price='$price', quantity='$quantity', user_id='$user_id', inventory_id='$inventory_id')"
    }


}