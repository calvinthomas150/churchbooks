package org.churchbooks.churchbooks.util;

import org.churchbooks.churchbooks.exception.StorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileSystemStorageServiceTest {

    String uploadDirectory = "src/test/resources/uploads";
    private final FileSystemStorageService fileSystemStorageService = new FileSystemStorageService(uploadDirectory);

    String filename = "mockFile.ofx";

    FileSystemStorageServiceTest() throws IOException {
    }

    @BeforeEach
    void createUploadDirectory() throws IOException {
        Files.createDirectories(Paths.get(uploadDirectory));

    }
    @AfterEach
    void deleteTestFiles() {
        FileSystemUtils.deleteRecursively(new File(uploadDirectory));
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
        URI uri = fileSystemStorageService.store(multipartFile);
        assertTrue(Paths.get(uri).toFile().exists());
    }

    @Test
    @DisplayName("Storing an uploaded file fails when the destination directory does not exist")
    void storeFails() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.png",
                "text/plain",
                new FileInputStream("src/test/resources/example.png")
        );
        FileSystemStorageService fileSystemStorageService1 = new FileSystemStorageService("bad/path");
        assertThrows(StorageException.class, () -> fileSystemStorageService1.store(multipartFile));
    }


}