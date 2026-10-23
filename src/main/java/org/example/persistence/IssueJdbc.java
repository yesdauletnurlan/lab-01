package org.example.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class IssueJdbc {

    private final JdbcTemplate jdbc;

    public IssueJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insert(UUID id, String businessKey, String status, String title) {
        jdbc.update("""
            INSERT INTO issue (id, business_key, status, title)
            VALUES (?, ?, ?, ?)
            """, id, businessKey, status, title);
    }

    public int count(String businessKey) {
        Integer n = jdbc.queryForObject(
                "SELECT count(*) FROM issue WHERE business_key = ?",
                Integer.class,
                businessKey);
        return n == null ? 0 : n;
    }
}