plugins {
    alias(libs.plugins.boot)
    alias(libs.plugins.spring.dependency.management)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.spring") version libs.versions.kotlin
}

group = "xyz.avalonxr"
version = "0.0.1"

repositories {
    mavenCentral()
    maven(
        url = "https://jitpack.io"
    )
}

dependencies {
    implementation(libs.vrc.api)
    implementation(libs.discord)
    implementation(libs.bundles.spring)

    // Unit testing support
    testImplementation(libs.bundles.testing)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}