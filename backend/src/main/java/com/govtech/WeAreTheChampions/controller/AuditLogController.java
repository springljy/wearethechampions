package com.govtech.WeAreTheChampions.controller;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import com.govtech.WeAreTheChampions.entity.AuditLog;
import com.govtech.WeAreTheChampions.service.AuditLogService;

import java.util.List;

@RestController
@RequestMapping("/api/auditlogs")
@AllArgsConstructor
public class AuditLogController {

    private AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        List<AuditLog> logs = auditLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }
}
