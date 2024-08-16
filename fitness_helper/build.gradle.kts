plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "ru.white"
version = "1.0.0"

subprojects {
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec", specDir.file("spec-fh-v1.yaml").toString())
}

tasks {
    arrayOf("build", "clean", "check").forEach { task ->
        create(task) {
            group = "build"
            dependsOn(subprojects.map { project -> project.getTasksByName(task, false) })
        }
    }
}