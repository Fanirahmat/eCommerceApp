package com.mfanir.ecommerceapp.Model

class Product {
    private var category: String? = ""
    private var date: String? = ""
    private var description: String? = ""
    private var image: String? = ""
    private var pid: String? = ""
    private var pname: String? = ""
    private var price: String? = ""
    private var seller: String? = ""
    private var time: String? = ""

    constructor()
    constructor(
        category: String?,
        date: String?,
        description: String?,
        image: String?,
        pid: String?,
        pname: String?,
        price: String?,
        seller: String?,
        time: String?
    ) {
        this.category = category
        this.date = date
        this.description = description
        this.image = image
        this.pid = pid
        this.pname = pname
        this.price = price
        this.seller = seller
        this.time = time
    }

    fun getCategory(): String? {
        return category
    }
    fun setCategory(category: String?){
        this.category = category
    }

    fun getDate(): String? {
        return date
    }
    fun setDate(date: String?){
        this.date = date
    }

    fun getDescription(): String? {
        return description
    }
    fun setDescription(description: String?){
        this.description = description
    }

    fun getImage(): String? {
        return image
    }
    fun setImage(image: String?){
        this.image = image
    }

    fun getPid(): String? {
        return pid
    }
    fun setPid(pid: String?){
        this.pid = pid
    }

    fun getPname(): String? {
        return pname
    }
    fun setPname(pname: String?){
        this.pname = pname
    }

    fun getPrice(): String? {
        return price
    }
    fun setPrice(price: String?){
        this.price = price
    }

    fun getSeller(): String? {
        return seller
    }
    fun setSeller(seller: String?){
        this.seller = seller
    }

    fun getTime(): String? {
        return time
    }
    fun setTime(time: String?){
        this.time = time
    }

}