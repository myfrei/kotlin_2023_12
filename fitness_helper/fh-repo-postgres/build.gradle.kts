plugins {
    id("build-jvm")
    alias(libs.plugins.muschko.remote)
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":fh-common"))
    implementation(project(":fh-repo-common"))
    implementation(libs.coroutines.core)
    implementation(libs.uuid)

    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.db.postgres)

    implementation(libs.bundles.exposed)

    testImplementation(project(":fh-repo-tests"))
    testImplementation(kotlin("test"))
}
