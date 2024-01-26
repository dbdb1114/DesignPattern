package part3_template_method_pattern;

public class CharDisplay extends AbstractDisplay{

    char printChar;
    public CharDisplay(char ch) {
        printChar = ch;
    }

    @Override
    public void open() {
        System.out.print("<< ");
    }

    @Override
    public void print() {
        System.out.print(printChar);
    }

    @Override
    public void close() {
        System.out.println(" >>");
    }
}
