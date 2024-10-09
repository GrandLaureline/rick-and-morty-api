plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val compileVersion: String by project
val minVersion: String by project

android {
    namespace = "org.mathieu.projet2"
    compileSdk = compileVersion.toInt()

    defaultConfig {
        applicationId = "org.mathieu.projet2"
        minSdk = minVersion.toInt()
        targetSdk = compileVersion.toInt()
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Compose part
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    // JVM Part
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

val navVersion: String by project
val koinVersion: String by project
val coroutinesVersion: String by project

dependencies {

    //Projects
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    implementation(project(":features:characters"))
    implementation(project(":features:locations"))

    //Compose
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //Koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation(project(":features:locations"))

}