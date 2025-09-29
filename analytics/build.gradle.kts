import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinCocoapods)
}

val localProperties = gradleLocalProperties(
    rootDir,
    providers
)

val appMetricaKey = localProperties.getProperty("APP_METRICA_KEY")

kotlin {
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/kmpBuildConfig/src/commonMain/kotlin")
        }
    }
}

val buildConfigDir = layout.buildDirectory.dir("generated/kmpBuildConfig/src/commonMain/kotlin")

tasks.register("generateKmpBuildConfig") {
    val outputDir = buildConfigDir.get().asFile
    val packageName = "ru.cappoeira.analytics"

    val apiKey = rootProject.findProperty("APP_METRICA_KEY") as? String ?: "default_key"

    outputs.dir(outputDir)

    doLast {
        val configFile = File(outputDir, "${packageName.replace(".", "/")}/BuildConfig.kt")
        configFile.parentFile.mkdirs()

        configFile.writeText(
            """
            package $packageName

            object BuildConfig {
                const val API_KEY: String = "$apiKey"
            }
            """.trimIndent()
        )
    }
}

tasks.named("compileKotlinMetadata") {
    dependsOn("generateKmpBuildConfig")
}

kotlin {

    androidLibrary {
        namespace = "ru.cappoeira.analytics"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {

        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "analyticsKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    cocoapods {

        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        ios.deploymentTarget = "14.0"
        version = "1.0"


        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = xcfName
            isStatic = true
        }

        pod("YandexMobileMetrica") {
            version = "4.5.2"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.yandex.android.mobmetrica)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
            }
        }

        iosMain {
            dependencies {

            }
        }
    }

}