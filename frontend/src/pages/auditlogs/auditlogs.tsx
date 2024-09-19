import React, { useState, useEffect } from 'react';
import './auditlogs.css';

interface AuditLogs {
    id: number;
    userId: number;
    action: string;
    entityType: string;
    entityId: number;
    details: string;
    timestamp: string;
}

const AuditLogs: React.FC = () => {
    const [logs, setLogs] = useState<AuditLogs[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/auditlogs')
          .then(response => response.json())
          .then(data => setLogs(data))
          .catch(error => console.error('Error fetching logs:', error));
      }, []);
    

    return (
        <div className="audit-logs wrapper">
            <h1>Audit Logs</h1>
            {logs.length > 0 ? (
                <table className="audit-logs-table">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Action</th>
                            <th>Entity Type</th>
                            <th>Details</th>
                            <th>Timestamp</th>
                        </tr>
                    </thead>
                    <tbody>
                        {logs.map(log => (
                            <tr key={log.id}>
                                <td>{log.userId}</td>
                                <td>{log.action}</td>
                                <td>{log.entityType}</td>
                                <td>{log.details}</td>
                                <td>{log.timestamp}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No audit logs</p>
            )}
        </div>
    );
};

export default AuditLogs;
