// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

buildscript {
    extra.apply {
        set("composeVersion", "2023.03.00")
        set("composeCompilerExtensionVersion", "1.5.3")
        set("appcompatVersion", "1.6.1")
        set("coreVersion", "1.12.0")
        set("datetimeVersion", "0.5.0")
        set("decomposeVersion", "2.2.2")
        set("lifecycleVersion", "2.7.0")
        set("activityVersion", "1.8.2")
        set("roomVersion", "2.6.1")
    }
}