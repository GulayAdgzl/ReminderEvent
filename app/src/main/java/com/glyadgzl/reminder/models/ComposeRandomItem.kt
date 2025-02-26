package com.glyadgzl.reminder.models

data class ComposeRandomItem(
    val name: String,
    val schedule: String,
    val type: String,
    val imageResId: Int,
    val description: String
)