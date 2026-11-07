package org.example.handler;

public record IssueResponse(
        String id,
        String businessKey,
        String status,
        String title
) {}