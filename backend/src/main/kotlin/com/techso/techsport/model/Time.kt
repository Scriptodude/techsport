package com.techso.techsport.model

data class Time(var timeInSeconds: Long) {
    var hours = timeInSeconds / 3600;
    var minutes = (timeInSeconds - hours * 3600) / 60;
    var seconds = timeInSeconds - hours * 3600 - minutes * 60
}