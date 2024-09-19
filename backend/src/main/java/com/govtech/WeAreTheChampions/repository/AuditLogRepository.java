package com.govtech.WeAreTheChampions.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.govtech.WeAreTheChampions.entity.AuditLog;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByOrderByTimestampDesc();
}
