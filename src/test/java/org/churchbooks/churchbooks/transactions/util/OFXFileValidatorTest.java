package org.churchbooks.churchbooks.transactions.util;

import org.apache.tika.Tika;
import org.churchbooks.churchbooks.transactions.exception.FileValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OFXFileValidatorTest {

    private final OFXFileValidator ofxFileValidator = new OFXFileValidator(new Tika());

    @Mock
    private Tika mockTika;

    @Test
    @DisplayName("A valid file input passes")
    void validate() throws IOException {
        MockMultipartFile validFile = new MockMultipartFile(
        "file",
        "example.ofx",
        "text/plain",
        new FileInputStream("src/test/resources/example.ofx")
        );
        assertDoesNotThrow(() -> ofxFileValidator.validate(validFile));
    }

    @Test
    @DisplayName("An empty file input fails")
    void shouldFailGivenEmptyFile() {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "blank.ofx",
                "text/plain",
                "".getBytes()
        );
        assertThrows(
                FileValidationException.class,
                () -> ofxFileValidator.validate(emptyFile)
        );
    }

    @Test
    @DisplayName("An invalid file type input with false header fails")
    void shouldFailGivenUnsupportedFileType() throws IOException {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "unsupported.php",
                "text/plain",
                new FileInputStream("src/test/resources/unsupported.php")
        );
        assertThrows(
                FileValidationException.class,
                () -> ofxFileValidator.validate(emptyFile)
        );
    }

    @Test
    @DisplayName("An invalid file type input whose type cannot be detected fails")
    void shouldFailGivenUndetectableFileType() throws IOException {
        when(mockTika.detect(any(InputStream.class))).thenThrow(new IOException());
        OFXFileValidator fileValidator = new OFXFileValidator(mockTika);
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "undetectable.txt",
                "text/plain",
                "Undetectable".getBytes()
        );
        assertThrows(
                FileValidationException.class,
                () -> fileValidator.validate(emptyFile)
        );
    }


}