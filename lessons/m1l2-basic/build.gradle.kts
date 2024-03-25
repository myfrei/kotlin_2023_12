plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    application
}

application {
    mainClass.set("ru.otus.otuskotlin.m1l2.MainKt")
}

dependencies {
    testImplementation(libs.kotlin.test)
}
