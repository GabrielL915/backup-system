package api.backup_system.domain.dto;

import java.time.LocalDateTime;

public class FileDTO {

    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedAt;
}
