package org.churchbooks.churchbooks.util;

import org.churchbooks.churchbooks.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemStorageService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${datastore.valid.ofx}") String uploadDirectory) {
        rootLocation = Paths.get(uploadDirectory);
    }

    @Override
    public void store(MultipartFile file, String filename) {
        Path destinationFile = this.rootLocation.resolve(filename).normalize().toAbsolutePath();
        logger.info("Destination file path: {}", destinationFile);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile);
        }catch (Exception e){
            throw new StorageException(e);
        }
    }

}
