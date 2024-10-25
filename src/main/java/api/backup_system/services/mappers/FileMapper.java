package api.backup_system.services.mappers;

import api.backup_system.domain.dto.FileDTO;
import api.backup_system.domain.entities.File;
import org.springframework.stereotype.Component;

@Component
public class FileMapper implements CRUDMapper<File, FileDTO> {

    @Override
    public FileDTO toDTO(File entity) {
        return FileDTO.builder()
                .id(entity.getId())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .fileSize(entity.getFileSize())
                .uploadedAt(entity.getUploadedAt())
                .build();
    }


    @Override
    public File toEntity(FileDTO dto) {
        return null;
    }
}
