package org.itstep.liannoi.reactivesearch.application.storage.users.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User constructor(
    @ColumnInfo(name = "UserId") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "FirstName") val firstName: String = ""
)
