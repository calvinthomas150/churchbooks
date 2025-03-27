package org.churchbooks.churchbooks.transactions.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileSystemStorageServiceTest {

    String uploadDirectory = "src/test/resources/uploads";
    private final FileSystemStorageService fileSystemStorageService = new FileSystemStorageService(uploadDirectory);

    String filename = "mockFile.ofx";

    @AfterEach
    void deleteTestFiles() throws IOException {
        Files.delete(Paths.get(uploadDirectory, filename));
    }

    @Test
    @DisplayName("An uploaded file is stored successfully")
    void store() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.ofx",
                "text/plain",
                new FileInputStream("src/test/resources/example.ofx")
        );

        fileSystemStorageService.store(multipartFile, filename);
        assertTrue(Paths.get(uploadDirectory, filename).toFile().exists());

    }
}