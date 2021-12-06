pluginManagement {
	repositories {
		gradlePluginPortal()
		maven("https://repo.mineinabyss.com/releases")
	}
	plugins {
		val kotlinVersion: String by settings
		kotlin("jvm") version kotlinVersion
		kotlin("plugin.serialization") version kotlinVersion
		id("com.github.johnrengelman.shadow") version "6.0.0"
	}

	val idofrontConventions: String by settings
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id.startsWith("com.mineinabyss.conventions"))
				useVersion(idofrontConventions)
		}
	}
}

val pluginName: String by settings

rootProject.name = pluginName
