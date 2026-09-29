plugins {
	id("net.fabricmc.fabric-loom")
}

repositories {
	maven("https://maven.shedaniel.me/")
	exclusiveContent {
		forRepository {
			maven("https://api.modrinth.com/maven/")
		}
		filter {
			includeGroup("maven.modrinth")
		}
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
	implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")

	implementation("net.fabricmc:fabric-language-kotlin:1.14.1+kotlin.2.4.20")

	implementation("me.shedaniel:RoughlyEnoughItems-fabric:${providers.gradleProperty("rei_version").get()}")

	// firmament 44.3.0+mc26.1
	implementation("maven.modrinth:IJNUBZ2a:J4SP0hOE")

	// skyhanni 9.1.0
	implementation("maven.modrinth:byNkmv5G:JuSRfyPe")
}

tasks.processResources {
	val version = version
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

java {
	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
	val projectName = project.name
	inputs.property("projectName", projectName)

	from("LICENSE") {
		rename { "${it}_$projectName" }
	}
}
