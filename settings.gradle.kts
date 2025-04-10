@file:Suppress("UnstableApiUsage")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "EpicGuard"

dependencyResolutionManagement {
    repositories {
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.alessiodp.com/releases/")
        maven("https://repo.jpenilla.xyz/snapshots/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

include(
    "core",
    "paper",
    "velocity",
    // "sponge"
)