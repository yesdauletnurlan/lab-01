package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IssueIdTest {

    @Test
    void nullOrBlankIdMustThrow() {
        assertThrows(IllegalArgumentException.class, () -> new IssueId(null));
        assertThrows(IllegalArgumentException.class, () -> new IssueId(""));
        assertThrows(IllegalArgumentException.class, () -> new IssueId("   "));
    }
}