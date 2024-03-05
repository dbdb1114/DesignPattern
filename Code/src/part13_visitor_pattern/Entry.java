package part13_visitor_pattern;

public abstract class Entry implements Element {
    public abstract String getName();
    public abstract int getSize();
    public abstract String getPath();
    public abstract void setPath(String path);
    public void printList(){
        printList("");
    }

    protected abstract void printList(String prefix);


    @Override
    public String toString() {
        return getName() + "(" + getSize() + ")";
    }
}
