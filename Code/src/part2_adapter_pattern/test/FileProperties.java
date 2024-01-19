package part2_adapter_pattern.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.util.Properties;

public class FileProperties implements FileIO{
    Properties fileProperties;
    File file;

    public FileProperties() {
        this.fileProperties = new Properties();
    }

    @Override
    public void readFormFile(String filename) throws IOException {
        fileProperties.load(new FileReader(filename));
    }

    @Override
    public void writeTofile(String filename) throws IOException {
        fileProperties.store(new FileWriter(filename),"Written by FileProperties");
    }

    @Override
    public void setValue(String key, String value) throws IOException {
        fileProperties.put(key,value);
    }

    @Override
    public String getValue(String key) throws IOException {
        return null;
    }
}
