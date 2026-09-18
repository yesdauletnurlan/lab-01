package example;

import org.example.IssueId;
import org.example.IssuePolicy;
import org.example.IssueStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class IssuePolicyTest {

    private final IssuePolicy policy = new IssuePolicy();

    @ParameterizedTest
    @CsvSource({
            "TODO, IN_PROGRESS",
            "IN_PROGRESS, DONE"
    })
    void allowedTransitionsShouldReturnNewStatus(IssueStatus from, IssueStatus to) {
        assertEquals(to, policy.move(from, to));
    }

    @ParameterizedTest
    @CsvSource({
            "DONE, TODO",
            "TODO, DONE"
    })
    void forbiddenTransitionsShouldThrowIllegalStateException(IssueStatus from, IssueStatus to) {
        assertThrows(IllegalStateException.class, () -> policy.move(from, to));
    }

    @Test
    void nullOrBlankIdMustThrow() {
        assertThrows(IllegalArgumentException.class, () -> new IssueId(null));
        assertThrows(IllegalArgumentException.class, () -> new IssueId(""));
        assertThrows(IllegalArgumentException.class, () -> new IssueId("   "));
    }
}