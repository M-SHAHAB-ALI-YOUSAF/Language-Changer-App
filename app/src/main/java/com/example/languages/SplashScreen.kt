package com.example.languages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.languages.databinding.FragmentSplashScreenBinding
import com.example.languages.roomdb.AppDatabase
import com.example.languages.roomdb.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreen : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val userDataDao by lazy {
        AppDatabase.getDatabase(requireContext()).userDataDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            (requireActivity().application as MyApplication).applyLanguage()
        }

        binding.run.setOnClickListener {
            lifecycleScope.launch {
                checkFirstTime()
            }
        }

        return binding.root
    }

    private suspend fun checkFirstTime() {
        val userData = withContext(Dispatchers.IO) {
            userDataDao.getUserData()
        }

        if (userData == null) {
            val newUser = UserData(isFirstTime = true, selectedLanguage = "English")
            userDataDao.insertUserData(newUser)
            navigateToLanguageSelection()
        } else {
            if (!userData.isFirstTime) {
                navigateToLanguageSelection()
            } else {
                navigateToDashboard()
            }
        }
    }

    private fun navigateToLanguageSelection() {
        findNavController().navigate(R.id.action_splashScreen_to_languageSet)
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_splashScreen_to_dashboard)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
