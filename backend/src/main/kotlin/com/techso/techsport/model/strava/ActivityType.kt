package com.techso.techsport.model.strava

enum class ActivityType(val french: String) {
    walk("Marche à pied"),
    hike("Randonnée pédestre"),
    inlineSkate("Patins à roues alignées"),
    ride("Vélo"),
    virtualRide("Vélo stationnaire"),
    run("Course à pied"),
    virtualRun("Course à pied sur tapis roulant");
}