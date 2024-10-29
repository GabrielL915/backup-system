package api.backup_system.resources;

import api.backup_system.domain.dto.FileDTO;
import api.backup_system.services.BackupService;
import api.backup_system.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileResources {


    private final FileService fileService;

    private final BackupService backupService;

    @PostMapping("/backup")
    public ResponseEntity<String> createBackup() throws IOException {
        String backupPath = backupService.createBackup();
        return ResponseEntity.ok("Backup created at: " + backupPath);
    }

    @PostMapping("/upload")
    public ResponseEntity<CompletableFuture<FileDTO>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        CompletableFuture<FileDTO> fileDTO = fileService.uploadFileAsync(file);
        return ResponseEntity.ok(fileDTO);

    }
}
