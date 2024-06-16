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
include(":core")
include(":foundation")
include(":android-foundation")
include(":ui:main")
include(":ui:add-repetition")
include(":ui:list")
include(":ui:repetition")
include(":ui:core")
