package com.techso.techsport.model.response

import com.techso.techsport.model.ActivityToValidate

data class AllActivitiesResponse(var activities: List<ActivityToValidate>, var pages: Int)