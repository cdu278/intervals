// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.android.library") version "8.4.0" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.24" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.24" apply false
    id("com.google.devtools.ksp") version "1.9.24-1.0.20"
}

buildscript {
    extra.apply {
        set("composeVersion", "2024.02.01")
        set("composeCompilerExtensionVersion", "1.5.14")
        set("appcompatVersion", "1.6.1")
        set("coreVersion", "1.12.0")
        set("datetimeVersion", "0.5.0")
        set("decomposeVersion", "2.2.2")
        set("lifecycleVersion", "2.7.0")
        set("activityVersion", "1.8.2")
        set("coroutinesVersion", "1.8.0")
        set("serializationVersion", "1.6.3")
        set("workmanagerVersion", "2.9.0")
        set("roomVersion", "2.6.1")
        set("materialVersion", "1.12.0")
        set("annotationVersion", "1.8.0")
    }
}