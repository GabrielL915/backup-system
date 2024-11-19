package org.backup.infraestructure;

import org.backup.domain.model.BackupReport;
import org.backup.domain.model.Receipt;
import org.backup.domain.service.ReceiptGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextReceiptGenerator implements ReceiptGenerator {
    @Override
    public Receipt generate(BackupReport report) {
        String date = new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        String content = """
                BACKUP RECEIPT
                -------------------------
                Date: %s
                Time: %s
                Files Copied: %d
                Total Size: %.2f MB
                Destination: %s
                Time Taken: %.2f seconds
                -------------------------
                Thank you for using The Backup System!
                """.formatted(
                date, time, report.fileCopied(),
                report.totalBytes() / (1024.0 * 1024), report.destinationPath(),
                report.timeTaken() / 1000.0
        );

        return new Receipt(content);
    }
}
