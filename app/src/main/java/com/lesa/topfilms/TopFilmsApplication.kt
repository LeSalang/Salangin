package com.lesa.topfilms

import android.app.Application
import com.lesa.topfilms.data.AppContainer
import com.lesa.topfilms.data.DefaultAppContainer

class TopFilmsApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = this)
    }
}