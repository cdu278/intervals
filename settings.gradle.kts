pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Passs"
include(":app")
include(":main")
include(":memo")
include(":foundation")
include(":android-foundation")
include(":ui:editor-flow")
include(":ui:memo-list")
include(":ui:repetition")
