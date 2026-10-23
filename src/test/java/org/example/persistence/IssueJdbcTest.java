package org.example.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = org.example.config.Application.class)
class IssueJdbcTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private IssueJdbc issues;

    @BeforeEach
    void setUp() throws Exception {
        jdbc.execute("DROP TABLE IF EXISTS issue");
        jdbc.execute(Files.readString(Path.of("src/main/resources/db/schema.sql")));
    }

    @Test
    void insertAndCount() {
        UUID id = UUID.randomUUID();
        issues.insert(id, "JIRA-1", "TODO", "Test issue");
        assertEquals(1, issues.count("JIRA-1"));
    }
}