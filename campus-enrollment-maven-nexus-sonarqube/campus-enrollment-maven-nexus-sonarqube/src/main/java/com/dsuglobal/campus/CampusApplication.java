package com.dsuglobal.campus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusApplication {
    public static void main(String[] args) {
        // The demo uses an H2 file database: create the data directory on first run.
        try {
            Files.createDirectories(Path.of("data"));
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot create ./data directory for H2", ex);
        }
        SpringApplication.run(CampusApplication.class, args);
    }
}
