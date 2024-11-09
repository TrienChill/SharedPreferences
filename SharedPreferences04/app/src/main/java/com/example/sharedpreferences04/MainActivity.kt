package com.example.sharedpreferences04

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedpreferences04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        // Hiển thị tên người dùng
        val username = sharedPreferences.getString("username", "")
        binding.welcomeText.text = "Chào mừng $username!"

        binding.logoutButton.setOnClickListener {
            // Xóa trạng thái đăng nhập
            sharedPreferences.edit().clear().apply()

            // Chuyển về màn hình đăng nhập
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}