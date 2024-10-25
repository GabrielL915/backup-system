package api.backup_system.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BackupDTO {

    private Long id;
    private String zipFileName;
    private String zipFilePath;
    private Long zipFileSize;
    private LocalDateTime backupDate;
    private List<FileDTO> files;
}
