package com.example.languages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.languages.databinding.FragmentLanguageSetBinding
import com.example.languages.roomdb.UserDataDao
import com.example.languages.roomdb.AppDatabase
import kotlinx.coroutines.launch

class LanguageSet : Fragment() {

    private var _binding: FragmentLanguageSetBinding? = null
    private val binding get() = _binding!!

    private lateinit var userDataDao: UserDataDao

    private var isListenerEnabled = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageSetBinding.inflate(inflater, container, false)

        userDataDao = AppDatabase.getDatabase(requireContext()).userDataDao()

        lifecycleScope.launch {
            val selectedLanguage = userDataDao.getSelectedLanguage()
            setSelectedLanguage(selectedLanguage)
        }

        binding.radioGroupEnglish.setOnCheckedChangeListener { _, _ ->
            if (isListenerEnabled) {
                isListenerEnabled = false
                uncheckOtherGroups(binding.radioGroupEnglish)
                selectLanguage("English")
                isListenerEnabled = true
            }
        }

        binding.radioGroupArabic.setOnCheckedChangeListener { _, _ ->
            if (isListenerEnabled) {
                isListenerEnabled = false
                uncheckOtherGroups(binding.radioGroupArabic)
                selectLanguage("Arabic")
                isListenerEnabled = true
            }
        }

        binding.radioGroupHindi.setOnCheckedChangeListener { _, _ ->
            if (isListenerEnabled) {
                isListenerEnabled = false
                uncheckOtherGroups(binding.radioGroupHindi)
                selectLanguage("Hindi")
                isListenerEnabled = true
            }
        }

        binding.radioGroupJapan.setOnCheckedChangeListener { _, _ ->
            if (isListenerEnabled) {
                isListenerEnabled = false
                uncheckOtherGroups(binding.radioGroupJapan)
                selectLanguage("Japanese")
                isListenerEnabled = true
            }
        }

        return binding.root
    }

    private fun setSelectedLanguage(language: String?) {
        when (language) {
            "English" -> binding.radioGroupEnglish.check(R.id.radio_button)
            "Arabic" -> binding.radioGroupArabic.check(R.id.radio2)
            "Hindi" -> binding.radioGroupHindi.check(R.id.hindiRadio)
            "Japanese" -> binding.radioGroupJapan.check(R.id.JapanRadio)
            else -> binding.radioGroupEnglish.check(R.id.radio_button)
        }
    }

    private fun selectLanguage(language: String) {
        lifecycleScope.launch {
            val currentLanguage = userDataDao.getSelectedLanguage()
            if (currentLanguage != language) {
                userDataDao.updateSelectedLanguage(language)

                val app = requireActivity().application as MyApplication
                app.applyLanguage()

                requireActivity().recreate()
            }
        }
    }

    private fun uncheckOtherGroups(selectedGroup: RadioGroup) {
        if (selectedGroup != binding.radioGroupEnglish) binding.radioGroupEnglish.clearCheck()
        if (selectedGroup != binding.radioGroupArabic) binding.radioGroupArabic.clearCheck()
        if (selectedGroup != binding.radioGroupHindi) binding.radioGroupHindi.clearCheck()
        if (selectedGroup != binding.radioGroupJapan) binding.radioGroupJapan.clearCheck()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
