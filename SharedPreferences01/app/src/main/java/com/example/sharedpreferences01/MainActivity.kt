package com.example.sharedpreferences01

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var inputLayout: TextInputLayout
    private lateinit var inputText: TextInputEditText
    private lateinit var saveButton: MaterialButton
    private lateinit var loadButton: MaterialButton

    private val PREFS_NAME = "MyPrefsFile"
    private val DATA_KEY = "savedText"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputLayout = findViewById(R.id.textInputLayout)
        inputText = findViewById(R.id.textInputEditText)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            val text = inputText.text.toString()
            if (text.isNotEmpty()) {
                saveData(text)
                Toast.makeText(this, "Đã lưu dữ liệu thành công!", Toast.LENGTH_SHORT).show()
                inputText.text?.clear()
            } else {
                inputLayout.error = "Vui lòng nhập dữ liệu"
            }
        }

        loadButton.setOnClickListener {
            loadData()
        }
    }

    private fun saveData(text: String) {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(DATA_KEY, text)
            apply()
        }
    }

    private fun loadData() {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sharedPref.contains(DATA_KEY)) {
            val savedText = sharedPref.getString(DATA_KEY, "")
            Toast.makeText(this, "Dữ liệu đã lưu: $savedText", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu đã lưu", Toast.LENGTH_SHORT).show()
        }
    }
}
