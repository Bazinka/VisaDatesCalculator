package com.visadatescalculator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Trip(
    @ColumnInfo(name = "enter_date") var enterDate: Date,
    @ColumnInfo(name = "leave_date") var leaveDate: Date,
    @ColumnInfo(name = "person_uid") var person_uid: Int
) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0


}