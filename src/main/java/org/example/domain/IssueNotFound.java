package org.example.domain;

public class IssueNotFound extends RuntimeException {
    public IssueNotFound(String id) {
        super("issue not found: " + id);
    }
}