package org.example.handler;

import org.example.domain.IssueNotFound;
import org.example.domain.IssueStatus;
import org.example.persistence.IssueJdbc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {

    private final IssueJdbc issues;

    public IssueController(IssueJdbc issues) {
        this.issues = issues;
    }

    @PostMapping
    public ResponseEntity<IssueResponse> create(@Valid @RequestBody CreateIssueRequest request) {
        // 1. Создаём UUID
        UUID id = UUID.randomUUID();

        // 2. Сохраняем в БД (метод из Лабы 3)
        issues.insert(id, request.businessKey(), IssueStatus.TODO.name(), request.title());

        // 3. Формируем ответ
        IssueResponse response = new IssueResponse(
                id.toString(),
                request.businessKey(),
                IssueStatus.TODO.name(),
                request.title()
        );

        // 4. Возвращаем 201 с Location
        return ResponseEntity
                .created(URI.create("/api/v1/issues/" + id))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponse> get(@PathVariable String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            // Если ID не UUID — это 400 (обработается в Advice)
            throw new IllegalArgumentException("id must be a UUID");
        }

        // Читаем из БД
        var row = issues.findById(uuid)
                .orElseThrow(() -> new IssueNotFound(id));

        IssueResponse response = new IssueResponse(
                row.id().toString(),
                row.businessKey(),
                row.status(),
                row.title()
        );

        return ResponseEntity.ok(response);
    }
}