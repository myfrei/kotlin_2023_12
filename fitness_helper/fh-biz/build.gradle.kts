plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.cor)
    implementation(project(":fh-common"))
    implementation(project(":fh-stubs"))
    testImplementation(libs.coroutines.test)
    testImplementation(kotlin("test"))
}

