package part3_template_method_pattern;

public class Main {
    public static void main(String[] args) {
        CharDisplay c = new CharDisplay('c');
        c.display();
        StringDisplay stringDisplay = new StringDisplay("Hello");
        stringDisplay.display();
    }
}
