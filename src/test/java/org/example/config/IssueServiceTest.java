package org.example.config;

import org.example.domain.DuplicateIssue;
import org.example.persistence.IssueJdbc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IssueServiceTest {

    @Autowired
    private IssueService service;

    @Autowired
    private IssueJdbc issues;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void setUp() throws Exception {
        jdbc.execute("DROP TABLE IF EXISTS issue");
        jdbc.execute(Files.readString(Path.of("src/main/resources/db/schema.sql")));
    }

    @Test
    void secondStatementRollsBack() {
        assertThrows(DuplicateIssue.class,
                () -> service.insertTwice("JIRA-42"));
        assertEquals(0, issues.count("JIRA-42"));
    }

    @Test
    void secondRequestKeepsTheFirst() {
        service.register(UUID.randomUUID(), "JIRA-43", "Printer");
        assertThrows(DuplicateIssue.class,
                () -> service.register(UUID.randomUUID(), "JIRA-43", "Again"));
        assertEquals(1, issues.count("JIRA-43"));
    }
}