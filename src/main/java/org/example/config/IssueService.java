package org.example.config;

import org.example.domain.DuplicateIssue;
import org.example.domain.IssueStatus;
import org.example.domain.Rule;
import org.example.persistence.IssueJdbc;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class IssueService {

    private final List<Rule> rules;
    private final IssueJdbc issues;

    public IssueService(List<Rule> rules, IssueJdbc issues) {
        this.rules = rules;
        this.issues = issues;
    }

    public IssueStatus move(IssueStatus from, IssueStatus to) {
        for (Rule rule : rules) {
            rule.check(from, to);
        }
        return to;
    }

    @Transactional
    public void insertTwice(String key) {
        issues.insert(UUID.randomUUID(), key, "TODO", "Printer dead");
        try {
            issues.insert(UUID.randomUUID(), key, "TODO", "Printer again");
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateIssue(key);
        }
    }

    @Transactional
    public void register(UUID id, String key, String title) {
        try {
            issues.insert(id, key, "TODO", title);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateIssue(key);
        }
    }
}