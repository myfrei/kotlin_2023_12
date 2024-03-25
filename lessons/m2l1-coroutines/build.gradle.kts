plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    // Homework Hard
    implementation(libs.okhttp3) // http client
    implementation(libs.jackson.module.kotlin) // from string to object

    testImplementation(libs.kotlin.test)
}
