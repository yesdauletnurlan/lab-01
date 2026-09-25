package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TransitionRuleTest {

    private final TransitionRule rule = new TransitionRule();

    @ParameterizedTest
    @CsvSource({
            "TODO, IN_PROGRESS",
            "IN_PROGRESS, DONE"
    })
    void allowedTransitionsShouldNotThrow(IssueStatus from, IssueStatus to) {
        assertDoesNotThrow(() -> rule.check(from, to));
    }

    @ParameterizedTest
    @CsvSource({
            "DONE, TODO",
            "TODO, DONE"
    })
    void forbiddenTransitionsShouldThrow(IssueStatus from, IssueStatus to) {
        assertThrows(IllegalStateException.class, () -> rule.check(from, to));
    }
}