plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.coroutines.core)

    // transport models
    implementation(project(":fh-common"))
    implementation(project(":fh-biz"))
}