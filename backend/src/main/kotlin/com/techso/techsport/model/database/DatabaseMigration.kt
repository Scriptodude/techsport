package com.techso.techsport.model.database

abstract class DatabaseMigration {
    abstract fun version() : DatabaseVersion
    abstract fun apply(): Boolean
}