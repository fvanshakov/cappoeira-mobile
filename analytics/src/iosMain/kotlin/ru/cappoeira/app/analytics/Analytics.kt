package ru.cappoeira.app.analytics

import cocoapods.AppMetricaCore.AMAAppMetrica
import cocoapods.AppMetricaCore.AMAAppMetricaConfiguration
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual object Analytics {

    actual fun initialize() {
        val configuration = AMAAppMetricaConfiguration(aPIKey = "")
        AMAAppMetrica.activateWithConfiguration(configuration)
    }
}