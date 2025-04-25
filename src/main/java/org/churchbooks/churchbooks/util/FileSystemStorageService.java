package org.churchbooks.churchbooks.util;

import org.churchbooks.churchbooks.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileSystemStorageService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${datastore.valid}") String uploadDirectory) {
        rootLocation = Paths.get(uploadDirectory);
    }

    @Override
    public URI store(MultipartFile file) throws IOException {
        // give the file a unique name before saving
        String filename = UUID.randomUUID().toString();
        Path destinationFile = this.rootLocation.resolve(filename).normalize().toAbsolutePath();
        logger.info("Destination file path: {}", destinationFile);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile);
        }catch (Exception e){
            throw new StorageException(e);
        }
        return destinationFile.toUri();
    }

}
