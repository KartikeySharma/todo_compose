package com.kartikey.todocompose

import android.app.Application

class TodoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }

}