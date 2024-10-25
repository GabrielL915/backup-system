package api.backup_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackupHistoryDTO {

    private Long id;
    private BackupDTO backup;
    private LocalDateTime createdAt;
}
