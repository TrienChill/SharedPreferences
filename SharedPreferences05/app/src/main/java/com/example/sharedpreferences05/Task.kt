package com.example.sharedpreferences05

data class Task(
    val id: Long = System.currentTimeMillis(),
    var title: String,
    var description: String = "",
    var isCompleted: Boolean = false
)