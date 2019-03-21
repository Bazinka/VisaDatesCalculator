package com.visadatescalculator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(@ColumnInfo(name = "name") var name: String?) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}