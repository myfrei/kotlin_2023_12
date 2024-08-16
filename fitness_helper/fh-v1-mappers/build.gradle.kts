plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":api-v1-jackson"))
    implementation(project(":fh-common"))
    testImplementation(kotlin("test-junit"))
}
