package com.techso.techsport.model.strava

import com.fasterxml.jackson.annotation.JsonProperty

enum class ActivityType {
    @JsonProperty("Walk")
    walk,
    @JsonProperty("Hike")
    hike,
    @JsonProperty("InlineSkate")
    inlineSkate,
    @JsonProperty("Ride")
    bike,
    @JsonProperty("VirtualRide")
    virtualRide,
    @JsonProperty("Run")
    run,
    @JsonProperty("VirtualRun")
    virtualRun,
}