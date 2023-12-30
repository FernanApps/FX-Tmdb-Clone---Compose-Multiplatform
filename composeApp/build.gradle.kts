//import jdk.tools.jlink.resources.plugins
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    //alias(libs.plugins.libres)
    //alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            
            //api(compose.foundation)
            api(compose.animation)
            val precompose_version = "1.5.10"

            api("moe.tlaster:precompose:$precompose_version")
            api("moe.tlaster:precompose-viewmodel:$precompose_version") // For ViewModel intergration


            implementation(libs.libres)
            implementation(libs.voyager.navigator)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings)
            implementation(libs.koin.core)
            implementation(libs.kstore)
            implementation(libs.window.size)

            implementation(libs.ktor.core)
            implementation(libs.com.google.code.gson)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.io.ktor.ktor.client.serialization)

            // Core library
            implementation(libs.kmpalette.core)

            // Optional extensions based on your image source
            implementation(libs.kmpalette.extensions.base64)
            implementation(libs.kmpalette.extensions.bytearray)
            implementation(libs.kmpalette.extensions.libres)
            implementation(libs.kmpalette.extensions.network)
            implementation(libs.kmpalette.extensions.resources)
            //implementation(libs.kmpalette.extensions.file)
            implementation(libs.materialKolor)


        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            api("io.github.kevinnzou:compose-webview-multiplatform:1.7.4")

        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.okhttp)
            api("io.github.kevinnzou:compose-webview-multiplatform:1.7.4")

        }

        jsMain.dependencies {
            implementation(compose.html.core)
        }

    }
}

android {
    namespace = "pe.fernan.kmp.tmdb"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        applicationId = "pe.fernan.kmp.tmdb.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    //sourceSets["main"].apply {
    //    manifest.srcFile("src/androidMain/AndroidManifest.xml")
    //    res.srcDirs("src/androidMain/resources")
    //}
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

compose.desktop {
    application {
        // all your other configuration, etc
        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED") // recommended but not necessary

        System.setProperty("compose.interop.blending", "true")

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }

        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "pe.fernan.kmp.tmdb.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}

//libres {
    // https://github.com/Skeptick/libres#setup
//}
//tasks.getByPath("jvmProcessResources").dependsOn("libresGenerateResources")
//tasks.getByPath("jvmSourcesJar").dependsOn("libresGenerateResources")
//tasks.getByPath("jsProcessResources").dependsOn("libresGenerateResources")

//buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
//}
