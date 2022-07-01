import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	war

	val kotlinVersion = "1.6.21"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"

	//  querydsl
	kotlin("kapt") version kotlinVersion

	// Kotlin JPA 개발 Guide
	kotlin("plugin.jpa") version kotlinVersion
	// allOpen에서 지정한 어노테이션으로 만든 클래스에 open 키워드 적용
	kotlin("plugin.allopen") version kotlinVersion
	// 인자 없는 기본 생성자를 자동 생성
	// - Hibernate가 사용하는 Reflection API에서 Entity를 만들기 위해 인자 없는 기본 생성자가 필요함
	kotlin("plugin.noarg") version kotlinVersion
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
val querydslVersion = "5.0.0"

allOpen {
	annotation("javax.persistence.Entity")
}
noArg {
	annotation("javax.persistence.Entity")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// querydsl
	implementation("com.querydsl:querydsl-jpa:$querydslVersion")
	kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
	kapt("org.springframework.boot:spring-boot-configuration-processor")

	// Dev
	testImplementation(kotlin("test"))
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
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
