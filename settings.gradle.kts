rootProject.name = "adapters-java"
include("testgear-java-commons")
include("testgear-adapter-testng")
include("testgear-adapter-junit4")
include("testgear-adapter-junit5")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "1.6.21"
    }
}
