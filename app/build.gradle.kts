import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

fun readLocalProperties(): Properties {
    val properties = Properties()
    InputStreamReader(FileInputStream(File("local.properties")), Charsets.UTF_8).use {
        properties.load(it)
    }
    return properties
}

android {
    namespace = "cdu278.intervals"
    compileSdk = 34

    signingConfigs {
        create("release") {
            val properties = readLocalProperties()
            storeFile = file(properties.getProperty("storeFile"))
            storePassword = properties.getProperty("storePassword")
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
        }
    }

    defaultConfig {
        applicationId = "cdu278.intervals"
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    implementation(project(":core"))
    implementation(project(":core:data"))
    implementation(project(":foundation"))
    implementation(project(":foundation:android"))
    implementation(project(":feature:main"))
    implementation(project(":feature:repetition"))

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

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:${rootProject.extra["serializationVersion"]}")

    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
}