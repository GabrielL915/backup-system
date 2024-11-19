package org.backup.infraestructure;

import org.backup.domain.model.BackupReport;
import org.backup.domain.service.BackupService;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileBackupService implements BackupService {
    @Override
    public BackupReport backup(File sourceDir, File destDir) throws Exception {
        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("O caminho de origem não é um diretório!");
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        } else if (!destDir.isDirectory()) {
            throw new IllegalArgumentException("O caminho de destino não é um diretório!");
        }

        int[] filesCopied = {0};
        long[] totalBytes = {0};
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        processDirectoryInParallel(sourceDir, destDir, executor, filesCopied, totalBytes);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        long timeTaken = System.currentTimeMillis() - startTime;

        return new BackupReport(filesCopied[0], totalBytes[0], destDir.getAbsolutePath(), timeTaken);
    }

    private void processDirectoryInParallel(File sourceDir, File destDir, ExecutorService executor, int[] filesCopied, long[] totalBytes) {
        for (File file : Objects.requireNonNull(sourceDir.listFiles())) {
            if (file.isDirectory()) {
                File newDestDir = new File(destDir, file.getName());
                newDestDir.mkdirs();

                executor.submit(() -> processDirectoryInParallel(file, newDestDir, executor, filesCopied, totalBytes));
            } else if (file.isFile()) {
                executor.submit(() -> {
                    File destFile = new File(destDir, file.getName());
                    try {
                        copyFile(file, destFile, filesCopied, totalBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    private void copyFile(File sourceFile, File destFile, int[] filesCopied, long[] totalBytes) throws IOException {
        try (FileChannel sourceChannel = FileChannel.open(sourceFile.toPath(), StandardOpenOption.READ);
             FileChannel destChannel = FileChannel.open(destFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            long size = sourceChannel.size();
            long position = 0;
            while (position < size) {
                position += sourceChannel.transferTo(position, Math.min(size - position, 64 * 1024 * 1024), destChannel);
            }

            synchronized (filesCopied) {
                filesCopied[0]++;
                totalBytes[0] += size;
            }
        }
    }
}