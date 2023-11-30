import dev.monosoul.jooq.RecommendedVersions
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    kotlin("jvm") version "1.9.21"
    id("dev.monosoul.jooq-docker") version "6.0.3"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${getKotlinPluginVersion()}"))

    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.7.3"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")

    implementation(platform("io.projectreactor:reactor-bom:2023.0.0"))
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("org.jooq:jooq-kotlin:${RecommendedVersions.JOOQ_VERSION}")
    implementation("org.jooq:jooq-kotlin-coroutines:${RecommendedVersions.JOOQ_VERSION}")

    jooqCodegen("org.postgresql:postgresql:42.7.0")
}

tasks.generateJooqClasses {
    basePackageName = "dev.monosoul.intellij.type.inference.issue.generated"
    usingJavaConfig {
        withName("org.jooq.codegen.KotlinGenerator")
        generate.apply {
            withPojosAsKotlinDataClasses(true)
            withKotlinNotNullRecordAttributes(true)
            withKotlinNotNullPojoAttributes(true)
            withKotlinNotNullInterfaceAttributes(true)
        }
    }
}