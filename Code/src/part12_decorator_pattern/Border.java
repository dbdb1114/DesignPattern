package part12_decorator_pattern;

public abstract class Border extends Display{
    protected Display display;

    protected Border(Display display){
        this.display = display;
    }
}
