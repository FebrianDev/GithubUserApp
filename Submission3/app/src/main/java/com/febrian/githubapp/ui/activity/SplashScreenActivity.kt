package com.febrian.githubapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.febrian.githubapp.databinding.ActivitySplashScreenBinding
import com.febrian.githubapp.ui.fragment.setting.SettingPreferences
import com.febrian.githubapp.ui.fragment.setting.SettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this).get(
            SettingViewModel::class.java
        )

        viewModel.setPreferences(pref)

        viewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY)
    }

    companion object {
        private const val DELAY = 800L
    }

}