plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "ru.white"
version = "1.0.0"

subprojects {
    group = rootProject.group
    version = rootProject.version
}