package com.techso.techsport.model.strava

import com.fasterxml.jackson.annotation.JsonProperty

enum class ActivityType(val french: String) {
    @JsonProperty("Walk")
    walk("Marche à pied"),
    @JsonProperty("Hike")
    hike("Randonnée pédestre"),
    @JsonProperty("InlineSkate")
    inlineSkate("Patins à roues alignées"),
    @JsonProperty("Ride")
    bike("Vélo"),
    @JsonProperty("VirtualRide")
    virtualRide("Vélo stationnaire"),
    @JsonProperty("Run")
    run("Course à pied"),
    @JsonProperty("VirtualRun")
    virtualRun("Course à pied sur tapis roulant");
}