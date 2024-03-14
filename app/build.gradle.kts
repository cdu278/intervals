plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "midget17468.passs"
    compileSdk = 34

    defaultConfig {
        applicationId = "midget17468.passs"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            rootProject.extra["composeCompilerExtensionVersion"] as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":main"))
    implementation(project(":memo"))
    implementation(project(":foundation"))
    implementation(project(":android-foundation"))
    implementation(project(":ui:repetition"))

    implementation("androidx.core:core-ktx:${rootProject.extra["coreVersion"]}")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycleVersion"]}")

    val activityVersion = rootProject.extra["activityVersion"]
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation("androidx.activity:activity-ktx:$activityVersion")

    implementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeVersion"]}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:${rootProject.extra["datetimeVersion"]}")

    implementation("com.arkivanov.decompose:decompose:${rootProject.extra["decomposeVersion"]}")

    implementation("androidx.work:work-runtime-ktx:${rootProject.extra["workmanagerVersion"]}")

    implementation("app.cash.sqldelight:android-driver:${rootProject.extra["sqldelightVersion"]}")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:${rootProject.extra["serializationVersion"]}")
}