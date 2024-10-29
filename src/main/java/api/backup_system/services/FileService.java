package api.backup_system.services;

import api.backup_system.domain.dto.FileDTO;
import api.backup_system.domain.entities.File;
import api.backup_system.domain.repository.FileRepository;
import api.backup_system.services.mappers.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class FileService {


    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public List<FileDTO> uploadFiles(List<MultipartFile> files) throws IOException, InterruptedException, ExecutionException {

        List<Callable<FileDTO>> tasks = new ArrayList<>();

        for (MultipartFile file : files) {
            tasks.add(() -> uploadFile(file));
        }

        List<Future<FileDTO>> futures = executorService.invokeAll(tasks);
        List<FileDTO> fileDTOS = new ArrayList<>();

        for (Future<FileDTO> future : futures) {
            fileDTOS.add(future.get());
        }

        return fileDTOS;
    }

    public FileDTO uploadFile(MultipartFile file) throws IOException {

        File savedFile = new File();
        savedFile.setFileName(extractFileName(file));
        savedFile.setFileType(extractFileType(file));
        savedFile.setFileSize(file.getSize());
        savedFile.setUploadedAt(LocalDateTime.now());
        savedFile.setContent(file.getBytes());

        return fileMapper.toDTO(fileRepository.save(savedFile));

    }

    private String extractFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    private String extractFileType(MultipartFile file) {
        return file.getContentType();
    }
}
