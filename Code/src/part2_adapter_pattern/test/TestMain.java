package part2_adapter_pattern.test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class TestMain {
    public static void main(String[] args) {
        FileIO f = new FileProperties();
        try {
            f.readFormFile("file.txt");
            f.setValue("width","1024");
            f.setValue("height","512");
            f.setValue("depth","32");
            f.writeTofile("newfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
