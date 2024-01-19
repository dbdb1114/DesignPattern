package part2_adapter_pattern.test;

import java.io.IOException;

public interface FileIO {
    void readFormFile(String filename) throws IOException;
    void writeTofile(String filename) throws IOException;
    void setValue(String key, String value) throws IOException;
    String getValue(String key) throws IOException;
}
