# Maven Quick Reference — DSU Global IT

> Instructor: Karthikeya · Java 21 · Spring Boot + Maven

## Maven fundamentals

**POM (`pom.xml`)**: project metadata, dependencies, build plugins, version and publishing destinations.  
**Coordinates**: `com.dsuglobal:campus-enrollment-api:1.0.0-SNAPSHOT`.  
**Artifact**: distributable Maven output (JAR/WAR/POM).  
**Plugin goal**: individual task (e.g. `dependency:tree`).  
**Lifecycle phase**: predefined stage (e.g. `compile`, `test`, `deploy`).

## Lifecycles and order

```text
CLEAN lifecycle   : pre-clean → clean → post-clean
DEFAULT lifecycle : validate → compile → test → package → verify → install → deploy
SITE lifecycle    : pre-site → site → post-site → site-deploy
```

The default sequence shown above omits intermediate phases. A command invoking a phase runs **all preceding phases in that lifecycle**. `mvn clean deploy` first deletes `target/`, then runs the default lifecycle through deploy.

## Commands to practice

| Goal | Command |
|---|---|
| Check Maven/JDK | `mvn -version` |
| Validate POM | `mvn validate` |
| Compile app | `mvn compile` |
| Run unit tests | `mvn test` |
| Run one test | `mvn -Dtest=StudentServiceTest test` |
| Build executable JAR | `mvn clean package` |
| Run app directly | `mvn spring-boot:run` |
| Verify + generate JaCoCo | `mvn clean verify` |
| Install to ~/.m2 | `mvn install` |
| Publish to remote Nexus | `mvn -s docs/settings-nexus.xml clean deploy` |
| Dependencies | `mvn dependency:tree` |
| Effective POM | `mvn help:effective-pom` |
| Offline | `mvn -o test` |
| Force refresh | `mvn -U clean verify` |
| Debug | `mvn -X test` |
| Delete build output | `mvn clean` |
| Scan code | `mvn org.sonarsource.scanner.maven:sonar-maven-plugin:5.5.0.6356:sonar -Dsonar.host.url=http://localhost:9000` |

## Clarifications students often get wrong

- **`install` ≠ installing Maven itself**: this lifecycle phase stores the built project in your machine's Maven cache.
- **`deploy` ≠ deploying your Java API to a server**: Maven's `deploy` phase publishes a build artifact to a remote repository. App deployment is a distinct activity.
- **Nexus ≠ SonarQube**: Nexus stores binaries; SonarQube evaluates source quality.
- **Unit tests ≠ static code analysis**: tests execute code; SonarScanner analyzes it.
- **JaCoCo ≠ SonarScanner**: JaCoCo produces coverage XML; SonarQube imports/displays it.
- **`pom.xml` contains repository URLs but NOT user passwords.** Credentials belong in a protected Maven `settings.xml` or secret injection.

## Key output directories

```text
target/classes/                    .class files
target/surefire-reports/           JUnit XML/text reports
target/site/jacoco/index.html     local coverage HTML
target/site/jacoco/jacoco.xml     coverage imported into SonarQube
target/campus-enrollment-api-1.0.0-SNAPSHOT.jar    runnable app
~/.m2/repository/                 Maven local artifact/dependency cache
```
