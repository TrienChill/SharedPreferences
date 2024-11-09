package com.example.sharedpreferences04

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences04.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        // Kiểm tra trạng thái đăng nhập
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding.loginButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Giả lập xác thực (trong thực tế nên kiểm tra với API/database)
            if (username == "trien" && password == "chill") {
                // Lưu trạng thái đăng nhập
                sharedPreferences.edit().apply {
                    putBoolean("is_logged_in", true)
                    putString("username", username)
                    apply()
                }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show()
            }
        }
    }
}