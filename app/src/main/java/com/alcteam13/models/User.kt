package com.alcteam13.models

class User(
    val user_id: String,
    var name: String,
    val profile_image: String,
    val secret_level: String
) {
    constructor() : this("","","","0")


    override fun toString(): String {
        return "User(name='$name', profile_image='$profile_image', user_id='$user_id', secret_level='$secret_level')"
    }
}