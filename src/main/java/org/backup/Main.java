package org.backup;

import org.backup.infraestructure.FileBackupService;
import org.backup.infraestructure.TextReceiptGenerator;
import org.backup.usecases.BackupUseCase;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File sourceDir = new File("");
        File destDir = new File("");

        BackupUseCase backupUseCase = new BackupUseCase(
                new FileBackupService(),
                new TextReceiptGenerator()
        );

        backupUseCase.execute(sourceDir, destDir);
    }
}