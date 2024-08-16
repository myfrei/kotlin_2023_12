plugins {
    id("build-jvm")
    application
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.muschko.java)
}

application {
    mainClass.set("ApplicationKt")
}

dependencies {

    implementation(kotlin("stdlib"))

    implementation(libs.rabbitmq.client)
    implementation(libs.jackson.databind)
    implementation(libs.coroutines.core)

    implementation(project(":fh-common"))
    implementation(project(":fh-app-common"))

    implementation(project(":api-v1-jackson"))
    implementation(project(":fh-v1-mappers"))

    implementation(project(":fh-biz"))
    implementation(project(":fh-stubs"))

    testImplementation(libs.testcontainers.rabbitmq)
    testImplementation(kotlin("test"))
}
