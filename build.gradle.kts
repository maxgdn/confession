import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.compose") version "0.4.0"
    id("com.squareup.sqldelight") version "1.5.0"
}

group = "com.confession.app"
version = "1.0.0"

repositories {
    jcenter()
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
    maven { url = uri("https://jetbrains.bintray.com/intellij-third-party-dependencies") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation("com.github.anastaciocintra:escpos-coffee:4.1.0")

    implementation("com.squareup.sqldelight:sqlite-driver:1.5.0")
    implementation("com.squareup.sqldelight:coroutines-extensions:1.5.0")

    implementation("io.insert-koin:koin-core:3.1.2")
    testImplementation("io.insert-koin:koin-test:3.1.2")
}

sqldelight {
    database("ConfessionDatabase") { // This will be the name of the generated database class.
        packageName = "com.confession.app"
        sourceFolders = listOf("sqldelight")
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.confession.app"
            packageVersion = "1.0.0"
        }
    }
}



