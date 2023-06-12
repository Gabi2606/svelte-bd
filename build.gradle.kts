import com.github.gradle.node.npm.task.NpmTask
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.distsDirectory
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    id("com.github.node-gradle.node") version "5.0.0"
}

group = "org.factoriaf5"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


val buildWithNpm = tasks.register<NpmTask>("buildWithNpm"){
    dependsOn(tasks.npmInstall)
    npmCommand.set(listOf("run", "build"))
}

val installFrontendDependencies = tasks.register<NpmTask>("installFrontendDependencies") {
    workingDir.set(file("./web-client"))
    args.set(listOf("install"))
}

val compileWebClient = tasks.register<NpmTask>("compileWebClient") {
    dependsOn(installFrontendDependencies)
    workingDir.set(file("./web-client"))
    args.set(listOf("run", "build"))
}

val copyFiles = tasks.register<Copy>("copyFiles") {
    dependsOn(compileWebClient)
    from("./web-client/dist")
    into("./src/main/resources/static")
}

val testFrontend = tasks.register<NpmTask>("testFrontend") {
    dependsOn("compileWebClient")
    workingDir.set(file("./web-client"))
    args.set(listOf("test"))
}

tasks.processResources {
    dependsOn(copyFiles)
}

tasks.test {
    dependsOn(testFrontend)
}