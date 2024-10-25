package api.backup_system.domain.dto;

import java.time.LocalDateTime;

public class BackupHistoryDTO {

    private Long id;
    private BackupDTO backup;
    private LocalDateTime createdAt;
}
