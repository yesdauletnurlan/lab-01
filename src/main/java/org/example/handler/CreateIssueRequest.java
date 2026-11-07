package org.example.handler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateIssueRequest(
        @NotBlank(message = "business key must not be blank")
        String businessKey,

        @NotBlank(message = "title must not be blank")
        @Size(max = 120, message = "size must be between 0 and 120")
        String title
) {}