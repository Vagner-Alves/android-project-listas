package com.example.android_study

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    var isCompleted: Boolean = false
)