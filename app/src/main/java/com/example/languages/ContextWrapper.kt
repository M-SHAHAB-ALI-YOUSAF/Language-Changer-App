package com.example.languages

import android.content.Context
import android.content.res.Configuration
import java.util.*

object ContextWrapper {
    fun updateLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
