plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":fh-common"))
    implementation(project(":fh-stubs"))
}

