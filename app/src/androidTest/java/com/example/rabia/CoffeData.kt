package com.example.rabia

data class Coffee(
    val kod: String,
    val ad: String,
    val aciklama: String,
    val resim: Int,
    val fiyat: Double,
)

val kahveListesi = listOf(
    Coffee("ESP-01", "Espresso", "Yoğun ve sert bir kahve türüdür.", R.drawable.espresso,fiyat = 150.00),
    Coffee("LAT-02", "Latte", "Yumuşak içimli, sütlü espresso türüdür.", R.drawable.latte,fiyat = 180.00),
    Coffee("CAP-03", "Cappuccino", "Espresso, süt ve yoğun köpükten oluşur.", R.drawable.cappuccino,fiyat = 160.00),
    Coffee("AMR-04", "Americano", "Espresso üzerine sıcak su eklenerek yapılır.", R.drawable.americano,fiyat = 150.00),
    Coffee("MOC-05", "Mocha", "Süt, çikolata ve espressonun birleşimi tatlı kahve.", R.drawable.mocha,fiyat = 200.00),
    Coffee("TRK-06", "Türk Kahvesi", "Cezvede pişirilen geleneksel köpüklü kahve.", R.drawable.turkkahvesi,fiyat = 100.00)
)