pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net")
		maven("https://maven.architectury.dev/")
		maven("https://maven.minecraftforge.net/")
		maven("https://repo.spongepowered.org/repository/maven-public/")
		maven("https://repo.sk1er.club/repository/maven-releases/")
	}

	resolutionStrategy {
		eachPlugin {
			when (requested.id.id) {
				"gg.essential.loom" -> useModule("gg.essential:architectury-loom:${requested.version}")
			}
		}
	}
}

rootProject.name = "Himmelbjerget"
