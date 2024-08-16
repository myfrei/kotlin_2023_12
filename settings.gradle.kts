rootProject.name = "kotlin_2023_12"
includeBuild("fitness_helper")
includeBuild("lessons")
includeBuild("fitness_helper_libs")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}