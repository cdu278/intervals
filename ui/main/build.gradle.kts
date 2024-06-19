plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "cdu278.intervals.main.ui"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            rootProject.extra["composeCompilerExtensionVersion"] as String
    }
}

dependencies {
    implementation(project(":foundation"))
    implementation(project(":foundation:android"))
    implementation(project(":core"))
    implementation(project(":core:data"))
    implementation(project(":ui:list"))
    implementation(project(":ui:add-repetition"))
    implementation(project(":ui:repetition"))

    implementation("androidx.core:core-ktx:${rootProject.extra["coreVersion"]}")

    implementation("androidx.appcompat:appcompat:${rootProject.extra["appcompatVersion"]}")

    implementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeVersion"]}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:${rootProject.extra["datetimeVersion"]}")

    implementation("com.arkivanov.decompose:decompose:${rootProject.extra["decomposeVersion"]}")

    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
}