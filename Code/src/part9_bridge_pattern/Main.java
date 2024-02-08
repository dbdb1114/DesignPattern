package part9_bridge_pattern;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Display d1 = new Display(new StringDisplayImpl("Hello, Korea."));
        Display d2 = new CountDisplay(new StringDisplayImpl("Hello, world."));
        CountDisplay d3 = new CountDisplay(new StringDisplayImpl("Hello, Universe."));
        CountDisplay d4 = new CountDisplay(new TxtDisplayImpl(new FileReader("/Users/yujeonghyeon/Desktop/스프링시큐리티_패스워드암호화(공유).txt")));

        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);
        d3.randomDisplay(5);
        d4.multiDisplay(1);
    }
}
