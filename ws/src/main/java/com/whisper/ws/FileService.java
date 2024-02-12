package com.whisper.ws;

import com.whisper.ws.user.configuration.WhisperProperties;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    WhisperProperties whisperProperties;

    Tika tika = new Tika();
    public String saveBase64StringAsFile(String image) {
        String filename = UUID.randomUUID().toString();

        Path path = getProfileImgPath(filename);
        File file = new File(filename);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            byte[] base64decoded = Base64.getDecoder().decode(image.split(",")[1]);
            outputStream.write(base64decoded);
            outputStream.close();
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String detectType(String value){
        return tika.detect(decodedImage(value));
    }
    private byte[] decodedImage(String encodedImage){
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

    public void deleteProfileImage(String image) {
        if(image == null) return;
        Path path = getProfileImgPath(image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getProfileImgPath(String filename){
        return Paths.get(whisperProperties.getStorage().getRoot(), whisperProperties.getStorage().getProfile(), filename);
    }
}
