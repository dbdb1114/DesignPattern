package part3_template_method_pattern;

import java.util.Scanner;

public class StringDisplay extends AbstractDisplay{
    private String printString;
    private int width;

    public StringDisplay(String printString) {
        this.printString = printString;
        this.width = printString.length();
    }

    @Override
    public void open() {
        printLine();
    }

    @Override
    public void print() {
        System.out.println("|" + this.printString + "|");
    }

    @Override
    public void close() {
        printLine();
    }

    public void printLine(){
        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
