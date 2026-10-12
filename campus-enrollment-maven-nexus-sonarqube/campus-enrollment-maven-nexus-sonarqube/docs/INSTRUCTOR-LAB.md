# Instructor Teaching Plan & Student Assessment

**Institute:** DSU Global IT  
**Instructor:** Karthikeya  
**Project:** CampusFlow Enrollment API — single app for Maven, Nexus, SonarQube  
**Target:** beginners with limited Java/DevOps exposure

## Instructor prerequisites

Prepare a lab host with Java 21, Maven, Docker Compose, at least 8 GiB RAM (preferably 12 GiB), and port forwarding for 8080/8081/9000. Review README steps and verify both tools initialize before the classroom. If using limited-resource browser labs, run Nexus and SonarQube on a separate, private VM; the app can still be demonstrated independently.

## Suggested 2.5–3.5 hour workshop

| Time | Topic | Live action | Expected student observation |
|---|---|---|---|
| 0–15 min | What is this app? | Open `http://localhost:8080`, create one record | Data appears and persists |
| 15–30 min | How Java builds | `java -version`, `mvn -version`, open `pom.xml` | Distinguish JDK and Maven |
| 30–55 min | Maven default lifecycle | `mvn validate`, `compile`, `test`, `package` | Changes in `target/` |
| 55–70 min | Dependencies, tests | `mvn dependency:tree`; read tests | Libraries & unit test output |
| 70–85 min | Coverage/verify | `mvn clean verify` | JaCoCo report exists |
| 85–95 min | Local installation | `mvn install`; browse `.m2` | Local JAR copy exists |
| 95–125 min | Nexus concepts / setup | `docker compose up -d`, browser on 8081 | Hosted repositories visible |
| 125–145 min | Publish the JAR | Export Nexus credentials, `mvn ... deploy` | JAR asset in maven-snapshots |
| 145–170 min | SonarQube | Browser 9000, token, Maven scanner | Issues and coverage visible |
| 170–190 min | Student challenge | Add a rule and unit test, rebuild and rescan | New output analyzed |

Startup time for Nexus/SonarQube is variable: prepare containers ahead of live teaching where possible.

## Student tasks (graded)

### Task 1 — Basic (10 marks)

Run `mvn -version`, locate `pom.xml`, and explain the roles of `groupId`, `artifactId`, and `version`. Run `mvn validate`, `compile`, and `test`. Submit command outputs and name two build output directories.

### Task 2 — Maven lifecycle (15 marks)

Run `mvn clean package`. Identify the generated JAR, then launch it. Register a student in the HTML web app. Explain why `mvn package` also executes tests.

### Task 3 — Business API testing (15 marks)

Create a course with capacity 1. Enroll one student. Enroll a different student and capture the **HTTP 409** response. Try creating an invalid email and capture **HTTP 400**. Explain what service class enforces seat limits.

### Task 4 — Code/unit-test change (20 marks)

Add a **new** unit test verifying `CourseService.list()` returns two mocked courses and run it with `mvn -Dtest=CourseServiceTest test`. Optional advanced extension: add a GET endpoint for `/api/courses/{id}` using controller/service/repository.

### Task 5 — Nexus publishing (20 marks)

Create or verify the `maven-snapshots` hosted repo. Deploy with `mvn -s docs/settings-nexus.xml clean deploy`. Submit a screenshot of Nexus Browse showing groupId/artifactId/version and the artifact file. Explain `install` vs `deploy`.

### Task 6 — SonarQube analysis (20 marks)

Run `mvn clean verify` and the Sonar Maven goal. Capture the Quality Gate status, actual issue count, and imported coverage percentage. Explain why SonarQube is different from JaCoCo and Nexus.

**Total:** 100 marks. Expected values such as test count, quality gate status, issue count, and coverage depend on project/tool versions; record what actually happened rather than forcing made-up values.

## Demo scripts and checkpoint commands

```bash
# In project directory
bash scripts/check-prereqs.sh
mvn validate
mvn compile
mvn test
mvn clean verify
ls target/*.jar
ls target/site/jacoco/
java -jar target/campus-enrollment-api-1.0.0-SNAPSHOT.jar
```

In separate terminal:

```bash
bash scripts/demo-api.sh
```

For Nexus and SonarQube, continue with README Sections 7–9 exactly; students should type commands themselves and verify results through both CLI and browser UI.

## Reflection questions

1. Why does `mvn clean` not remove dependencies in `.m2`?
2. If `mvn test` fails, should `mvn deploy` normally upload a JAR?
3. How does the `distributionManagement` server ID link to credentials?
4. Why do `-SNAPSHOT` and release artifacts belong in different hosted repos?
5. Why is a quality gate not a replacement for testing?
6. What problems arise from committing Maven/Nexus credentials to GitHub?
7. Which additional controls would you add before this app goes to production?

## Expected conceptual answers

1. Clean removes the project's `target/` output; the Maven local cache is separate.
2. No. A normal lifecycle build stops on failed tests unless someone deliberately skips them.
3. Matching IDs in `pom.xml` distributionManagement and Maven's `<servers>` settings entries.
4. Snapshots iterate rapidly and may be timestamped; releases should be stable/immutable.
5. Static checks and executed tests catch different problem classes.
6. Password/token exposure, misuse, audit and compliance failures.
7. AuthN/AuthZ, production DB and migrations, pipeline, HTTPS, least-privilege, backups, monitoring and stronger test coverage.
