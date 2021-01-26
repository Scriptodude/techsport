package com.techso.techsport.model

data class Time(val timeInSeconds: Long) {
    val hours = timeInSeconds / 3600;
    val minutes = (timeInSeconds - hours * 3600) / 60;
    val seconds = timeInSeconds - hours * 3600 - minutes * 60
}