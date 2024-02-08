package part9_bridge_pattern;

public class PatternDisplay extends Display{
    public PatternDisplay(DisplayImpl impl) {
        super(impl);
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void print() {
        super.print();
    }

    @Override
    public void close() {
        super.close();
    }
}
