package org.example.config;

import org.example.domain.IssueStatus;
import org.example.domain.Rule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    private final List<Rule> rules;

    public IssueService(List<Rule> rules) {
        this.rules = rules;
    }

    public IssueStatus move(IssueStatus from, IssueStatus to) {
        for (Rule rule : rules) {
            rule.check(from, to);
        }
        return to;
    }
}