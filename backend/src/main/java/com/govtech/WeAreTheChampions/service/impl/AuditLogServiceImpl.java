package com.govtech.WeAreTheChampions.service.impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.List;
import java.time.LocalDateTime;

import com.govtech.WeAreTheChampions.repository.AuditLogRepository;
import com.govtech.WeAreTheChampions.entity.AuditLog;
import com.govtech.WeAreTheChampions.service.AuditLogService;

@Service
@AllArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private AuditLogRepository auditLogRepository;

    @Override
    public void logAction(Long userId, String action, String entityType, Long entityId, String details) {
        AuditLog log = AuditLog.builder()
            .userId(userId)
            .action(action)
            .entityType(entityType)
            .entityId(entityId)
            .details(details)
            .timestamp(LocalDateTime.now())
            .build();

        auditLogRepository.save(log);
    }

    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }
}
