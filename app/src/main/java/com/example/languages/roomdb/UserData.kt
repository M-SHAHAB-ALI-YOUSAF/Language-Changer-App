package com.example.languages.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "is_first_time") var isFirstTime: Boolean = true,
    @ColumnInfo(name = "selected_language") var selectedLanguage: String? = null
)
