plugins {
    java
    id("org.springframework.boot") version "2.2.13.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")
    //Database
    implementation("org.hibernate:hibernate-validator:8.0.0.Final")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //Web application
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    //Swagger
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    //Mapstruct
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
    //Security
    implementation("org.springframework.boot:spring-boot-starter-security:2.1.18.RELEASE")
    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.security:spring-security-test")
}
