package org.churchbooks.churchbooks.util;

import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.churchbooks.churchbooks.exception.FileValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Validates an ofx file using the file extension and content  */
public class OFXFileValidator implements FileValidator {

    private static final Logger logger = LoggerFactory.getLogger(OFXFileValidator.class);
    private final Tika tika;

    public OFXFileValidator(){this.tika = new Tika();}

    public OFXFileValidator(Tika tika){
        this.tika = tika;
    }

    @Override
    public void validate(MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("File validation failed, empty file");
            throw new FileValidationException("Empty file");
        }

        try(InputStream inputStream = file.getInputStream()) {
            String detectedType = tika.detect(inputStream);
            logger.info("Detected file type: {}", detectedType);
            if(!isValidType(detectedType)){
                throw new FileValidationException("Invalid file type");
            }
        } catch (IOException exception){
            logger.info("Failed to detect file extension: ", exception);
            throw new FileValidationException("Cannot detect file type");
        }

    }

    private boolean isValidType(String detectedType){
       Set<String> validTypes = new HashSet<>();
        validTypes.add(MediaType.parse("application/x-ofx").toString());
        validTypes.add(MediaType.APPLICATION_XML.toString());
        validTypes.add(MediaType.TEXT_PLAIN.toString());
       return validTypes.contains(detectedType);
    }


}
