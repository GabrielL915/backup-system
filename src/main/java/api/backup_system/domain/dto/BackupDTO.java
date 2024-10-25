package api.backup_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackupDTO {

    private Long id;
    private String zipFileName;
    private String zipFilePath;
    private Long zipFileSize;
    private LocalDateTime backupDate;
    private List<FileDTO> files;
}
