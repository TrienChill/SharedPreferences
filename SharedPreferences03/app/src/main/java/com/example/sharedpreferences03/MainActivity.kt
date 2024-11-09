package com.example.sharedpreferences03

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences03.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private var launchCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Get current launch count and increment it
        launchCount = sharedPref.getInt("launch_count", 0) + 1

        // Save the new count
        with(sharedPref.edit()) {
            putInt("launch_count", launchCount)
            apply()
        }

        // Set up the UI
        setupUI()
    }

    private fun setupUI() {
        // Update launch count text with animation
        binding.tvLaunchCount.text = launchCount.toString()

        // Animate the counter
        val animator = ValueAnimator.ofInt(0, launchCount)
        animator.duration = 1000 // 1 second animation
        animator.addUpdateListener { animation ->
            binding.tvLaunchCount.text = animation.animatedValue.toString()
        }
        animator.start()

        // Set up reset button click listener
        binding.btnReset.setOnClickListener {
            // Show confirmation dialog
            // Xây dựng và hiển thị AlertDialog
            val dialog = AlertDialog.Builder(this)
                .setTitle("Reset Counter")
                .setMessage("Are you sure you want to reset the launch counter?")
                .setPositiveButton("Reset") { _, _ ->
                    resetCounter()  // Thực hiện hành động reset counter
                }
                .setNegativeButton("Cancel", null)  // Không làm gì nếu người dùng nhấn "Cancel"
                .create()

                // Hiển thị hộp thoại
            dialog.show()
        }
    }

    private fun resetCounter() {
        // Reset counter in SharedPreferences
        with(sharedPref.edit()) {
            putInt("launch_count", 0)
            apply()
        }

        // Update UI
        launchCount = 0
        binding.tvLaunchCount.text = "0"

        // Show success message
        Snackbar.make(binding.root, "Counter has been reset", Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(R.color.pink_primary))
            .setTextColor(getColor(R.color.cream_light))
            .show()
    }
}