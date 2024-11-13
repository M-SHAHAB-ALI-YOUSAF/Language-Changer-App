package com.example.languages

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.languages.roomdb.AppDatabase
import kotlinx.coroutines.runBlocking
import java.util.Locale

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        applyLanguage()
    }

    fun applyLanguage() {
        val selectedLanguage = getSelectedLanguageFromDatabase()
        val languageCode = getLanguageCode(selectedLanguage ?: "English")
        setLocale(applicationContext, languageCode)
    }

    fun getSelectedLanguageFromDatabase(): String? {
        return runBlocking {
            val userDataDao = AppDatabase.getDatabase(applicationContext).userDataDao()
            userDataDao.getSelectedLanguage()
        }
    }

    private fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun getLanguageCode(language: String): String {
        return when (language) {
            "English" -> "en"
            "Arabic" -> "ar"
            "Hindi" -> "hi"
            "Japanese" -> "ja"
            else -> "en"
        }
    }
}
