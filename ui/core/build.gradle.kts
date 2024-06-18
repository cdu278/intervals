plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "cdu278.intervals.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":foundation"))
    implementation(project(":data:core"))

    implementation("androidx.core:core-ktx:${rootProject.extra["coreVersion"]}")

    implementation("androidx.appcompat:appcompat:${rootProject.extra["appcompatVersion"]}")

    implementation("com.arkivanov.decompose:decompose:${rootProject.extra["decomposeVersion"]}")
}