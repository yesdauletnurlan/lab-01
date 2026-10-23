package org.example.domain;

public class DuplicateIssue extends RuntimeException {
    public DuplicateIssue(String businessKey) {
        super("duplicate issue: " + businessKey);
    }
}