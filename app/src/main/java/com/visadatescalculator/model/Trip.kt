package com.visadatescalculator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class Trip(
    @ColumnInfo(name = "enter_date") var enterDate: DateTime,
    @ColumnInfo(name = "leave_date") var leaveDate: DateTime,
    @ColumnInfo(name = "person_uid") var person_uid: Int
) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0


}