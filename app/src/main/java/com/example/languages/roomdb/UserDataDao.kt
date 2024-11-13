package com.example.languages.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDataDao {

    @Insert
    suspend fun insertUserData(userData: UserData)

    @Query("SELECT * FROM user_data WHERE id = 1")
    suspend fun getUserData(): UserData?

    @Query("UPDATE user_data SET is_first_time = :isFirstTime WHERE id = 1")
    suspend fun updateFirstTimeFlag(isFirstTime: Boolean)

    @Query("UPDATE user_data SET selected_language = :language WHERE id = 1")
    suspend fun updateSelectedLanguage(language: String)

    @Query("SELECT selected_language FROM user_data WHERE id = 1")
    suspend fun getSelectedLanguage(): String?
}
