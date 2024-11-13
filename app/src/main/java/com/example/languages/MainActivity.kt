package com.example.languages

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.languages.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun attachBaseContext(newBase: Context) {
        val app = newBase.applicationContext as MyApplication
        val languageCode = app.getLanguageCode(app.getSelectedLanguageFromDatabase() ?: "English")
        val context = ContextWrapper.updateLocale(newBase, Locale(languageCode))
        super.attachBaseContext(context)
    }
}
