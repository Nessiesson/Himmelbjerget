plugins {
	id("gg.essential.multi-version.root")
	id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

preprocess {
	createNode("1.8.9-forge", 10809, "srg")
}
