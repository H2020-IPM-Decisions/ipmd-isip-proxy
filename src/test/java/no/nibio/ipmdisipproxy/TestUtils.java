package no.nibio.ipmdisipproxy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @since 1.0.0
 */
public class TestUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJsonFromFile(String path, Class<T> clazz) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)));
            return objectMapper.readValue(json, clazz);
        } catch (IOException ioe) {
            return null;
        }
    }

    public static String readStringFromFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException ioe) {
            return null;
        }
    }
}
