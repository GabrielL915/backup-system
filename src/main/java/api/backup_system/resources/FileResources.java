package api.backup_system.resources;

import api.backup_system.domain.dto.FileDTO;
import api.backup_system.services.BackupService;
import api.backup_system.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileResources {

    @Autowired
    private FileService fileService;

    @Autowired
    private BackupService backupService;

    @PostMapping("/backup")
    public ResponseEntity<String> createBackup() throws IOException {
        String backupPath = backupService.createBackup();
        return ResponseEntity.ok("Backup created at: " + backupPath);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(
            @RequestParam("file") MultipartFile file) {

        try {
            FileDTO fileDTO = fileService.uploadFile(file);
            return ResponseEntity.ok(fileDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
