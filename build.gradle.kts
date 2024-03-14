// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.21" apply false
    id("app.cash.sqldelight") version "2.0.1" apply false
}

buildscript {
    extra.apply {
        set("composeVersion", "2024.02.01")
        set("composeCompilerExtensionVersion", "1.5.7")
        set("appcompatVersion", "1.6.1")
        set("coreVersion", "1.12.0")
        set("datetimeVersion", "0.5.0")
        set("decomposeVersion", "2.2.2")
        set("lifecycleVersion", "2.7.0")
        set("activityVersion", "1.8.2")
        set("coroutinesVersion", "1.8.0")
        set("serializationVersion", "1.6.3")
        set("workmanagerVersion", "2.9.0")
        set("sqldelightVersion", "2.0.1")
    }
}