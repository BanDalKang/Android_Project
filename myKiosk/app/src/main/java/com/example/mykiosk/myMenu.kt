package com.example.mykiosk

class myMenu(private val itemName: String, private val itemPrice: Double, private val itemDescription: String) : SHAKESHACK() {
    override val name: String
        get() = itemName

    override val price: Double
        get() = itemPrice

    override val description: String
        get() = itemDescription

    override fun displayInfo(index: Int) {
        println("$index. $itemName | W ${"%.1f".format(itemPrice)} | $itemDescription")
    }
}