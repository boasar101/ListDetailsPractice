package com.example.listdetailspractice

import android.app.Application

class ListDetailsPracticeApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
