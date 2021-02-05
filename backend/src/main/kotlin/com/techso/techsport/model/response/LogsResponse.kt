package com.techso.techsport.model.response

import com.techso.techsport.model.ManualTimeLog

data class LogsResponse(var logs: List<ManualTimeLog>, var pages: Int)