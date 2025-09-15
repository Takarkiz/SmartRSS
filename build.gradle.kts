plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.androidLint) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    // Apply Detekt to all modules
    pluginManager.apply("io.gitlab.arturbosch.detekt")

    dependencies {
        // Jetpack Compose-specific rules for Detekt
        add("detektPlugins", "io.nlopez.compose.rules:detekt:0.4.7")
    }

    // Ensure detekt runs as part of verification
    tasks.matching { it.name == "check" }.configureEach {
        dependsOn("detekt")
    }
}