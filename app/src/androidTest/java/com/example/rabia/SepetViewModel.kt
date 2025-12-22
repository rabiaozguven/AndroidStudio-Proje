package com.example.rabia

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class SepetViewModel {

    private val _sepetListesi = mutableStateListOf<Coffee>()
    val sepetListesi: SnapshotStateList<Coffee> = _sepetListesi
    fun urunEkle(urun:Coffee ) {
        _sepetListesi.add(urun)
    }
    fun urunCikar(urun: Coffee) {
        _sepetListesi.remove (urun)
    }
}