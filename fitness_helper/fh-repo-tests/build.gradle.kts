plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":fh-common"))
    implementation(project(":fh-stubs"))
    implementation(project(":fh-repo-common"))
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.test)

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation(kotlin("test"))
}
