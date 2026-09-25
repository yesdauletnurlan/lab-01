package org.example.domain;

public interface Rule {
    void check(IssueStatus from, IssueStatus to);
}