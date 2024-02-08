package part9_bridge_pattern;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtDisplayImpl extends DisplayImpl{
    private FileReader txt;

    public TxtDisplayImpl(FileReader txt) {
        this.txt = txt;
    }

    @Override
    public void rawOpen() {
        printLine();
    }

    @Override
    public void rawPrint() {
        int ch;
        while (true) {
            try {
                if (((ch = txt.read()) != -1)){
                    System.out.print((char) ch);
                } else {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void rawClose() {
        printLine();
    }
    private void printLine(){
        System.out.print("+");
        for (int i = 0; i < 20; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
