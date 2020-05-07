package com.example.newsapplication

import android.app.Application
import com.example.newsapplication.di.AppModule
import com.example.newsapplication.di.ApplicationComponent
import com.example.newsapplication.di.DaggerApplicationComponent

class App: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()

    }
}