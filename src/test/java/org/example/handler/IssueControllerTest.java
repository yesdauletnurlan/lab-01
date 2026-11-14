package org.example.handler;

import org.example.domain.DuplicateIssue;
import org.example.domain.IssueNotFound;
import org.example.persistence.IssueJdbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = IssueController.class)
@ContextConfiguration(classes = org.example.config.Application.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueJdbc issues;

    @Test
    void createReturns201() throws Exception {
        mockMvc.perform(post("/api/v1/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"businessKey\":\"JIRA-1\",\"title\":\"Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.businessKey").value("JIRA-1"));
    }

    @Test
    void blankTitleReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"businessKey\":\"JIRA-1\",\"title\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION"));
        verifyNoInteractions(issues);
    }

    @Test
    void getMissingReturns404() throws Exception {
        when(issues.findById(any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/issues/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void duplicateReturns409() throws Exception {
        doThrow(new DuplicateIssue("JIRA-1")).when(issues).insert(any(), any(), any(), any());
        mockMvc.perform(post("/api/v1/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"businessKey\":\"JIRA-1\",\"title\":\"Test\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE"));
    }
}