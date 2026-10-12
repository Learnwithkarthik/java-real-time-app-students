# Optional: run the SAME Maven-built executable JAR in a Java runtime container.
# First run: mvn clean package
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --uid 10001 appuser && mkdir /app/data && chown appuser:appuser /app/data
COPY target/campus-enrollment-api-1.0.0-SNAPSHOT.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
