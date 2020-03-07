package com.work.criminalintent.kt

import java.util.*

class Crime @JvmOverloads constructor(val id: UUID = UUID.randomUUID()) {
    var title: String? = null
    var date = Date()
    var isSolved = false
    var suspect: String? = null
    fun getPhotoFileName(): String = "IMG_$id.jpg"
}
