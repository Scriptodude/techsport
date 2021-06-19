package com.techso.techsport.model.database

import org.springframework.data.annotation.Id

data class DatabaseVersion(@Id val value: Int) : Comparable<DatabaseVersion>  {
    override fun compareTo(other: DatabaseVersion): Int {
        return this.value.compareTo(other.value)
    }
}
