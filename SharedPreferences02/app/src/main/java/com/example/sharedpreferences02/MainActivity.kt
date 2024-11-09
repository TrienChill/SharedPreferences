package com.example.sharedpreferences02

// MainActivity.kt

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.card.MaterialCardView
import android.content.SharedPreferences
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var darkModeSwitch: SwitchMaterial
    private lateinit var largeFontSwitch: SwitchMaterial
    private lateinit var sampleText: TextView
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var cardView: MaterialCardView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFERENCES_NAME = "UserSettings"
        const val DARK_MODE_KEY = "dark_mode"
        const val LARGE_FONT_KEY = "large_font"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        // Ánh xạ các view
        initializeViews()

        // Khôi phục trạng thái đã lưu
        restoreSettings()

        // Thiết lập listeners
        setupListeners()
    }

    private fun initializeViews() {
        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        largeFontSwitch = findViewById(R.id.largeFontSwitch)
        sampleText = findViewById(R.id.sampleText)
        rootLayout = findViewById(R.id.rootLayout)
        cardView = findViewById(R.id.cardView)
    }

    private fun restoreSettings() {
        val isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false)
        darkModeSwitch.isChecked = isDarkMode
        applyDarkMode(isDarkMode)

        val isLargeFont = sharedPreferences.getBoolean(LARGE_FONT_KEY, false)
        largeFontSwitch.isChecked = isLargeFont
        applyFontSize(isLargeFont)
    }

    private fun setupListeners() {
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            applyDarkMode(isChecked)
            saveSettings(DARK_MODE_KEY, isChecked)
        }

        largeFontSwitch.setOnCheckedChangeListener { _, isChecked ->
            applyFontSize(isChecked)
            saveSettings(LARGE_FONT_KEY, isChecked)
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            // Áp dụng chế độ tối
            rootLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.background_dark))
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_background_dark))
            sampleText.setTextColor(ContextCompat.getColor(this, R.color.text_primary_dark))
            darkModeSwitch.setTextColor(ContextCompat.getColor(this, R.color.text_primary_dark))
            largeFontSwitch.setTextColor(ContextCompat.getColor(this, R.color.text_primary_dark))
            sampleText.setBackgroundColor(ContextCompat.getColor(this, R.color.card_background_dark))
        } else {
            // Áp dụng chế độ sáng
            rootLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_background))
            sampleText.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            darkModeSwitch.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            largeFontSwitch.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            sampleText.setBackgroundColor(ContextCompat.getColor(this, R.color.cream))
        }
    }

    private fun applyFontSize(isLarge: Boolean) {
        sampleText.textSize = if (isLarge) 24f else 16f
    }

    private fun saveSettings(key: String, value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }
}