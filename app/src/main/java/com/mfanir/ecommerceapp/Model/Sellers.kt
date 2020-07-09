package com.mfanir.ecommerceapp.Model

class Sellers {
    private var name: String = ""
    private var email: String = ""
    private var username: String = ""
    private var phone: String = ""
    private var profile: String = ""
    private var password: String = ""

    constructor()
    constructor(
        name: String,
        email: String,
        username: String,
        phone: String,
        profile: String,
        password: String
    ) {
        this.name = name
        this.email = email
        this.username = username
        this.phone = phone
        this.profile = profile
        this.password = password
    }

    fun getPassword(): String? {
        return password
    }
    fun setPassword(password: String){
        this.password = password
    }

    fun getName(): String? {
        return name
    }
    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String? {
        return email
    }
    fun setEmail(email: String) {
        this.email = email
    }

    fun getUsername(): String? {
        return username
    }
    fun setUsername(username: String) {
        this.username = username
    }

    fun getPhone(): String? {
        return phone
    }
    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getProfile(): String? {
        return profile
    }
    fun setProfile(profile: String) {
        this.profile = profile
    }

}