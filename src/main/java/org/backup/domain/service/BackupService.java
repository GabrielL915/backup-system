package org.backup.domain.service;

import org.backup.domain.model.BackupReport;

import java.io.File;

public interface BackupService {
    BackupReport backup(File sourceDir, File destDir) throws Exception;
}
