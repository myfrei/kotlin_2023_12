rootProject.name = "fitness_helper"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":api-v1-jackson")
include(":fh-common")
include(":fh-v1-mappers")
include(":fh-biz")
include(":fh-stubs")
include(":fh-app-spring")
include(":fh-app-common")
include(":fh-app-rabbit")
include(":fh-repo-common")
include(":fh-repo-inmemory")
include(":fh-repo-stubs")
include(":fh-repo-tests")
include(":fh-repo-postgres")