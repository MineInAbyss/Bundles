val idofrontVersion: String by project

plugins {
	id("com.mineinabyss.conventions.kotlin")
	id("com.mineinabyss.conventions.papermc")
	id("com.mineinabyss.conventions.slimjar")
	id("com.mineinabyss.conventions.copyjar")
	id("com.mineinabyss.conventions.publication")
	kotlin("plugin.serialization")
}

repositories {
	mavenCentral()
	maven("https://repo.mineinabyss.com/releases")
}

dependencies {
	// Shaded
	implementation("com.mineinabyss:idofront:$idofrontVersion")
}