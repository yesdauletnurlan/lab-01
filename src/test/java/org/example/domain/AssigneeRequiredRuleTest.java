package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssigneeRequiredRuleTest {

    @Test
    void withoutAssigneeCannotStartWork() {
        AssigneeRequiredRule rule = new AssigneeRequiredRule(false);
        assertThrows(IllegalStateException.class,
                () -> rule.check(IssueStatus.TODO, IssueStatus.IN_PROGRESS));
    }

    @Test
    void withAssigneeCanStartWork() {
        AssigneeRequiredRule rule = new AssigneeRequiredRule(true);
        assertDoesNotThrow(
                () -> rule.check(IssueStatus.TODO, IssueStatus.IN_PROGRESS));
    }
}