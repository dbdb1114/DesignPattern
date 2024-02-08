package part9_bridge_pattern;

public class CountDisplay extends Display{
    public CountDisplay(DisplayImpl impl){
        super(impl);
    }
    public void multiDisplay(int times){
        open();
        for (int i = 0; i < times; i++) {
            print();
        }
        close();
    }

    public void randomDisplay(int times){
        int randomTimes = (int) (Math.random() * times);
        open();
        for (int i = 0; i < randomTimes; i++) {
            print();
        }
        close();
    }
}
