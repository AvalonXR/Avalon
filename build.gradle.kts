import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.detekt)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.spring") version libs.versions.kotlin
    kotlin("plugin.jpa") version libs.versions.kotlin
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
    detektPlugins(libs.detekt.formatting)

    implementation(libs.kotlin.reflect)
    implementation(libs.vrc.api)
    implementation(libs.discord)
    implementation(libs.bundles.spring)

    runtimeOnly(libs.h2.database)
    // Unit testing support
    testImplementation(libs.bundles.testing)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt.yml")
    baseline = file("$projectDir/config/baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "21"

    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "21"
}