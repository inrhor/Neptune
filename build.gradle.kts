plugins {
    java
    id("io.izzel.taboolib") version "1.26"
    id("org.jetbrains.kotlin.jvm") version "1.5.20"
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
            name("AuthMe")
        }
        prefix("Neptune")
    }
    classifier = null
    version = "6.0.1-8"
}

repositories {
    mavenCentral()
    maven("https://mvn.lumine.io/repository/maven-public")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")
    compileOnly("ink.ptms.core:v11604:11604:all")
    compileOnly("ink.ptms.core:v11600:11600:all")
    compileOnly("ink.ptms.core:v11500:11500:all")
    compileOnly("ink.ptms.core:v11400:11400:all")
    compileOnly("ink.ptms.core:v11300:11300:all")
    compileOnly("ink.ptms.core:v11200:11200:all")
    compileOnly("ink.ptms.core:v11100:11100:all")
    compileOnly("ink.ptms.core:v11000:11000:all")
    compileOnly("ink.ptms.core:v10900:10900:all")
    compileOnly("ink.ptms:Adyeshach:1.3.13")
    compileOnly("fr.xephi:authme:5.6.0-SNAPSHOT")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}