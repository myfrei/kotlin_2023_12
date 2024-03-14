plugins {
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    // Только для lombock!!
    java
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = libs.versions.jvm.target.get()
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        browser {
            testTask {
//                useKarma {
//                    // Выбираем браузеры, на которых будет тестироваться
//                    useChrome()
//                    useFirefox()
//                }
                // Без этой настройки длительные тесты не отрабатывают
                useMocha {
                    timeout = "100s"
                }
            }
        }
    }
    linuxX64()
    macosArm64()

    // Description of modules corresponding to our target platforms
    //  common - common code that we can use on different platforms
    //  for each target platform, we can specify our own specific dependencies
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val jvmMain by getting {
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        // dependencies from npm
        val jsMain by getting {
            dependencies {
                implementation(npm("js-big-decimal", "~1.3.4"))
                implementation(npm("is-sorted", "~1.0.5"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        // С 1.9.20 можно так
        nativeMain {
        }
        nativeTest {
        }

    }
}

// Только для lombock!!
dependencies {
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}
