package org.backup.usecases;

import org.backup.domain.model.BackupReport;
import org.backup.domain.model.Receipt;
import org.backup.domain.service.BackupService;
import org.backup.domain.service.ReceiptGenerator;

import java.io.File;

public class BackupUseCase {

    private final BackupService backupService;
    private final ReceiptGenerator receiptGenerator;

    public BackupUseCase(BackupService backupService, ReceiptGenerator receiptGenerator) {
        this.backupService = backupService;
        this.receiptGenerator = receiptGenerator;
    }

    public void execute(File sourceDir, File destDir) {
        try {
            BackupReport report = backupService.backup(sourceDir, destDir);
            Receipt receipt = receiptGenerator.generate(report);

            System.out.println(receipt.content());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
