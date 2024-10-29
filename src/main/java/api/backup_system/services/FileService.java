package api.backup_system.services;

import api.backup_system.domain.dto.FileDTO;
import api.backup_system.domain.entities.File;
import api.backup_system.domain.repository.FileRepository;
import api.backup_system.services.mappers.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileService {


    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public List<CompletableFuture<FileDTO>> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadFileAsync)
                .toList();
    }

    @Async("fileUploadExecutor")
    public CompletableFuture<FileDTO> uploadFileAsync(MultipartFile file) {
        try {
            File savedFile = new File();
            savedFile.setFileName(extractFileName(file));
            savedFile.setFileType(extractFileType(file));
            savedFile.setFileSize(file.getSize());
            savedFile.setUploadedAt(LocalDateTime.now());
            savedFile.setContent(file.getBytes());

            FileDTO fileDTO = fileMapper.toDTO(fileRepository.save(savedFile));

            return CompletableFuture.completedFuture(fileDTO);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private String extractFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    private String extractFileType(MultipartFile file) {
        return file.getContentType();
    }
}
