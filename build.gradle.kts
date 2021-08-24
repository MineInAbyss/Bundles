plugins {
	id("com.mineinabyss.conventions.kotlin")
	kotlin("plugin.serialization")
	id("com.mineinabyss.conventions.papermc")
	id("com.mineinabyss.conventions.publication")
}

repositories {
	mavenCentral()
	maven("https://repo.mineinabyss.com/releases")
}

dependencies {
	// Shaded
	implementation("com.mineinabyss:idofront:1.17.1-0.6.23")
}