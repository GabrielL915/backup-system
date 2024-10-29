package api.backup_system.services;

import api.backup_system.domain.entities.File;
import api.backup_system.domain.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BackupService {


    private final Path backupDirectory = Paths.get("backups-generated");

    @Autowired
    private FileRepository fileRepository;

    public BackupService() throws IOException {
        if (!Files.exists(backupDirectory)) {
            Files.createDirectories(backupDirectory);
        }
    }


    public String createBackup() throws IOException {
        List<File> files = fileRepository.findAll();
        String zipFileName = "backup_" + System.currentTimeMillis() + ".zip";
        Path zipFilePath = backupDirectory.resolve(zipFileName);

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()))) {
            for (File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getFileName());
                zipOut.putNextEntry(zipEntry);


                ByteArrayInputStream fileContentStream = new ByteArrayInputStream(file.getContent());
                fileContentStream.transferTo(zipOut);

                zipOut.closeEntry();
            }
        }

        return zipFilePath.toString();
    }
}
