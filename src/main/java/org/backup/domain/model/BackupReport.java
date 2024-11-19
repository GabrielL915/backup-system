package org.backup.domain.model;

public record BackupReport(int fileCopied, long totalBytes, String destinationPath, long timeTaken) {
}
