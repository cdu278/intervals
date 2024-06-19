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
include(":core:data")
include(":foundation")
include(":foundation:android")
include(":feature:main")
include(":feature:add-repetition")
include(":feature:repetition-list")
include(":feature:repetition")
