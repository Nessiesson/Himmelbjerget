pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven("https://repo.essential.gg/repository/maven-public")
		maven("https://maven.architectury.dev")
		maven("https://maven.fabricmc.net")
		maven("https://maven.minecraftforge.net")
	}
	plugins {
		id("gg.essential.multi-version.root") version "0.6.5"
	}
}

val modName: String by settings
rootProject.name = modName
rootProject.buildFileName = "root.gradle.kts"

listOf(
	"1.8.9-forge"
).forEach { version ->
	include(":$version")
	project(":$version").apply {
		projectDir = file("versions/$version")
		buildFileName = "../../build.gradle.kts"
	}
}
