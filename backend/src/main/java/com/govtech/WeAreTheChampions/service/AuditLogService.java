package com.govtech.WeAreTheChampions.service;

import java.util.List;

import com.govtech.WeAreTheChampions.entity.AuditLog;

public interface AuditLogService {

    void logAction(Long userId, String action, String entityType, Long entityId, String details);
    List<AuditLog> getAllLogs();
}
