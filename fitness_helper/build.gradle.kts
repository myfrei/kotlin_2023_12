plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.muschko.remote) apply false
    alias(libs.plugins.muschko.java) apply false
}

group = "com.fitness.helper"
version = "1.0.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("spec-fh-v1.yaml").toString())
}

tasks {
    arrayOf("build", "clean", "check").forEach { task ->
        create(task) {
            group = "build"
            dependsOn(subprojects.map { project -> project.getTasksByName(task, false) })
        }
    }
}