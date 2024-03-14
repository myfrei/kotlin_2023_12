rootProject.name = "lessons"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include("m1l1-first")
include("m1l2-basic")
include("m1l3-func")
include("m1l4-oop")
include("m1l5-dsl")
include("m2l1-coroutines")
include("m2l2-flows")
include("m2l3-kmp")
//Отключено для избежания проблем с неустановленными библиотеками для интероперабельности
//include("m2l4-1-interop")
//include("m2l4-2-rust")
include("m2l4-3-jni")
include("m2l5-gradle")


include(":m2l5-gradle:sub1:ssub1", ":m2l5-gradle:sub1:ssub2")

include(":m2l5-gradle-sub2")
project(":m2l5-gradle-sub2").apply {
    projectDir = file("m2l5-gradle/sub2")
    name = "m2l5-custom-sub2"
}
