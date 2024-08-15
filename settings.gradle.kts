rootProject.name = "kotlin_2023_12"
includeBuild("fitness_helper")
includeBuild("lessons")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}