plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("app.cash.sqldelight")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutinesVersion"]}")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:${rootProject.extra["datetimeVersion"]}")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:${rootProject.extra["serializationVersion"]}")
    
    implementation("com.arkivanov.decompose:decompose:${rootProject.extra["decomposeVersion"]}")
}