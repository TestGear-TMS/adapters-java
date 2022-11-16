rootProject.name = "adapters-java"
include("testgear-java-commons")
include("testgear-adapter-testng")
include("testgear-adapter-junit4")
include("testgear-adapter-junit5")
include("testgear-adapter-cucumber4")
include("testgear-adapter-cucumber5")
include("testgear-adapter-cucumber6")
include("testgear-adapter-cucumber7")
include("testgear-adapter-jbehave")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "1.6.21"
    }
}
