import org.apache.commons.lang3.SystemUtils

plugins {
	idea
	java
	id("gg.essential.loom") version "0.10.0.+"
	id("dev.architectury.architectury-pack200") version "0.1.3"
	id("com.github.johnrengelman.shadow") version "8.1.1"
}

//Constants:

val baseGroup: String by project
val version: String by project
val mixinGroup = "$baseGroup.mixins"
val modid: String by project
val modName: String by project
val transformerFile = file("src/main/resources/accesstransformer.cfg")

// Toolchains:
java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

// Minecraft configuration:
loom {
	launchConfigs {
		"client" {
			property("mixin.debug.verbose", "true")
			property("mixin.debug.export", "true")
			arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
		}
	}
	runConfigs {
		"client" {
			if (SystemUtils.IS_OS_MAC_OSX) {
				// This argument causes a crash on macOS
				vmArgs.remove("-XstartOnFirstThread")
			}
		}
		"server" {
			isIdeConfigGenerated = false
		}
	}
	forge {
		pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
		mixinConfig("mixins.$modid.json")
		if (transformerFile.exists()) {
			println("Installing access transformer")
			accessTransformer(transformerFile)
		}
	}
	@Suppress("UnstableApiUsage")
	mixin {
		defaultRefmapName.set("mixins.$modid.refmap.json")
	}
}

sourceSets.main {
	output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

// Dependencies:

repositories {
	mavenCentral()
	maven("https://repo.essential.gg/repository/maven-public/")
	maven("https://repo.spongepowered.org/repository/maven-public/")

}

val shadowImpl: Configuration by configurations.creating {
	configurations.implementation.get().extendsFrom(this)
}

dependencies {
	minecraft("com.mojang:minecraft:1.8.9")
	mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
	forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

	shadowImpl("gg.essential:loader-launchwrapper:1.2.3")
	shadowImpl("gg.essential:essential-1.8.9-forge:17141+gd6f4cfd3a8") {
		exclude(module = "asm")
		exclude(module = "asm-commons")
		exclude(module = "asm-tree")
		exclude(module = "gson")
		exclude(module = "vigilance")
	}

	implementation(annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0-beta.4")!!)
	annotationProcessor("org.spongepowered:mixin:0.8.7:processor")
	compileOnly("org.spongepowered:mixin:0.8.5")
	annotationProcessor("com.github.bsideup.jabel:jabel-javac-plugin:0.4.2")
	compileOnly("com.github.bsideup.jabel:jabel-javac-plugin:0.4.2")
}

// Tasks:

tasks.withType(JavaCompile::class) {
	sourceCompatibility = "17"
	options.release = 8
	options.encoding = "UTF-8"

	javaCompiler = javaToolchains.compilerFor {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

tasks.withType(org.gradle.jvm.tasks.Jar::class) {
	archiveBaseName.set("z$modName")
	manifest.attributes.run {
		this["FMLCorePluginContainsFMLMod"] = "true"
		this["ForceLoadAsMod"] = "true"

		this["TweakClass"] = "gg.essential.loader.stage0.EssentialSetupTweaker"
		this["TweakOrder"] = "0"
		this["MixinConfigs"] = "mixins.$modid.json"
		if (transformerFile.exists())
			this["FMLAT"] = "${modid}_at.cfg"
	}
}

tasks.processResources {
	inputs.property("version", project.version)
	inputs.property("modid", modid)
	inputs.property("basePackage", baseGroup)

	filesMatching(listOf("mcmod.info")) {
		expand(inputs.properties)
	}

	rename("accesstransformer.cfg", "META-INF/${modid}_at.cfg")
}


val remapJar by tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
	archiveClassifier.set("")
	from(tasks.shadowJar)
	input.set(tasks.shadowJar.get().archiveFile)
}

tasks.jar {
	archiveClassifier.set("without-deps")
	destinationDirectory.set(layout.buildDirectory.dir("intermediates"))
}

tasks.shadowJar {
	destinationDirectory.set(layout.buildDirectory.dir("intermediates"))
	archiveClassifier.set("non-obfuscated-with-deps")
	configurations = listOf(shadowImpl)
	doLast {
		if (configurations.isNotEmpty()) {
			configurations.forEach {
				println("Copying dependencies into mod: ${it.files}")
			}
		}
	}

	// If you want to include other dependencies and shadow them, you can relocate them in here
	fun relocate(name: String) = relocate(name, "$baseGroup.deps.$name")
}

tasks.assemble.get().dependsOn(tasks.remapJar)
