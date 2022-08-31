import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.7.10"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val props: Map<String, *> = project.properties

val name = rootProject.name
group = props["plugin.group"] as String
description = props["plugin.description"] as String
version = props["plugin.version"] as String

val java = props["gradle.java"] as String
val kotlin = props["gradle.kotlin"] as String
val kotlinApi = props["gradle.kotlin-api"] as String
val pkg = "$group.${description!!.toLowerCase()}"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://repo.clojars.org/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation("com.hazelcast:hazelcast:5.1.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin")
    implementation("com.esotericsoftware.kryo:kryo5:5.3.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(java))
}

tasks {

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = java
        kotlinOptions.apiVersion = kotlinApi
    }

    shadowJar {
        minimize()
        archiveFileName.set("$name-${project.version}.jar")
        archiveVersion.set(java)
        relocate("org.jetbrains.kotlin", "$pkg.kotlin")
    }

    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    name = "$name"
    description = "${project.description}"
    main = "$pkg.$name"
    version = project.version.toString()
    apiVersion = "1.18"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("Pesto")
}