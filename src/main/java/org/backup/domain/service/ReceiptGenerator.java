package org.backup.domain.service;

import org.backup.domain.model.BackupReport;
import org.backup.domain.model.Receipt;

public interface ReceiptGenerator {
    Receipt generate(BackupReport report);
}
