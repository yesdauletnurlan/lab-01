package org.example.domain;
public record IssueId(String value) {
    public IssueId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("IssueId cannot be null or blank");
        }
    }
}