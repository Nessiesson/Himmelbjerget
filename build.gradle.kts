import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import gg.essential.gradle.util.noServerRunConfigs
import net.fabricmc.loom.task.RemapJarTask

plugins {
	id("gg.essential.multi-version")
	id("gg.essential.defaults")
	id("com.github.johnrengelman.shadow")
}

val baseGroup: String by project
val version: String by project
val modName: String by project
val modid = modName.lowercase()

project.version = version
project.group = baseGroup
base.archivesName.set(modName)

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
	noServerRunConfigs()
	runConfigs.getByName("client") {
		property("mixin.debug.verbose", "true")
		property("mixin.debug.export", "true")
		programArgs("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
	}
	forge.mixinConfig("mixins.$modid.json")
	@Suppress("UnstableApiUsage")
	mixin.defaultRefmapName.set("mixins.$modid.refmap.json")
}

sourceSets {
	val dummy by creating
	main {
		output.setResourcesDir(java.classesDirectory)
		dummy.compileClasspath += compileClasspath
		compileClasspath += dummy.output
		output.setResourcesDir(java.classesDirectory)
	}
}

repositories {
	maven("https://repo.spongepowered.org/repository/maven-public")
}

val shade: Configuration by configurations.creating {
	configurations.implementation.get().extendsFrom(this)
}

dependencies {
	implementation("gg.essential:loader-launchwrapper:1.2.3")
	implementation("gg.essential:essential-1.8.9-forge:17141+gd6f4cfd3a8")

	annotationProcessor("org.spongepowered:mixin:0.8.7:processor")
	compileOnly("org.spongepowered:mixin:0.8.4")

	annotationProcessor("com.github.bsideup.jabel:jabel-javac-plugin:0.4.2")
	compileOnly("com.github.bsideup.jabel:jabel-javac-plugin:0.4.2")
}

tasks.withType(JavaCompile::class) {
	sourceCompatibility = "17"
	options.release = 8

	javaCompiler = javaToolchains.compilerFor {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

tasks {
	named<Jar>("jar") {
		manifest.attributes(
			mapOf(
				"FMLCorePluginContainsFMLMod" to true,
				"ForceLoadAsMod" to true,
				"TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker",
				"TweakOrder" to "0",
				"MixinConfigs" to "mixins.$modid.json"
			)
		)
		dependsOn(shadowJar)
		enabled = false
	}
	named<RemapJarTask>("remapJar") {
		inputFile.set(shadowJar.get().archiveFile)
	}
	named<ShadowJar>("shadowJar") {
		archiveClassifier.set("dev")
		configurations = listOf(shade)
	}
}
