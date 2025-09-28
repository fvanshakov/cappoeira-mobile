package ru.cappoeira.app.androidApp

import android.app.Application

class CapoAndroidApplication : Application() {

    init {
        appInstance = this
    }

    companion object {
        lateinit var appInstance: CapoAndroidApplication
    }
}