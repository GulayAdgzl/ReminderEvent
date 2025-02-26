package com.glyadgzl.reminder.models

data class ReminderData(
    val taskTitle: String,
    val date: Calendar,
    val note: String,
    val frequency: ReminderFrequency
)
