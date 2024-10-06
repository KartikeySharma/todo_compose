package com.kartikey.todocompose

import android.content.Context
import com.kartikey.todocompose.data.TodoDataSource
import com.kartikey.todocompose.data.room.TodoDatabase

object Graph {
    // initialise dependencies
    lateinit var database: TodoDatabase
        private set

    // create when we actually need it
    val todoRepository by lazy {
        TodoDataSource(database.todoDao())
    }

    fun provide(context: Context) {
        database = TodoDatabase.getDatabase(context)
    }

}