package org.example.domain;

import org.example.domain.IssueStatus;

public interface Rule {
    void check(IssueStatus from, IssueStatus to);
}