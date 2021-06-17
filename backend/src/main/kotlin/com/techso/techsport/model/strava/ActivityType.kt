package com.techso.techsport.model.strava

enum class ActivityType(val french: String, val english: String) {
    walk("Marche à pied", "Walk"),
    hike("Randonnée pédestre", "Hike"),
    inlineSkate("Patins à roues alignées", "RollerBlade"),
    ride("Vélo", "Bike"),
    virtualRide("Vélo stationnaire", "Stationnary Bike"),
    run("Course à pied", "Running"),
    virtualRun("Course à pied sur tapis roulant", "Treadmill Running");
}