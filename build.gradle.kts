plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.40"
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
}

taboolib {
    install("common",
            "common-5",
            "module-chat",
            "module-configuration",
            "module-lang",
            "module-metrics",
            "module-nms",
            "module-nms-util",
            "platform-bukkit")
    description {
        contributors {
            name("inrhor")
            desc("Packet Player Cosmetics")
        }
        dependencies {
            name("Adyeshach")
            name("AuthMe").optional(true)
        }
        prefix("Neptune")
    }
    classifier = null
    version = "6.0.9-4"
}

repositories {
    mavenCentral()
    maven("https://mvn.lumine.io/repository/maven-public")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly("ink.ptms.core:v11900:11900-minimize:mapped")
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")
    compileOnly("ink.ptms:Adyeshach:1.4.21")
    compileOnly("fr.xephi:authme:5.6.0-SNAPSHOT")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            groupId = project.group.toString()
        }
    }
}