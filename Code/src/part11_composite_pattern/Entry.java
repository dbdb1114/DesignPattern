package part11_composite_pattern;

public abstract class Entry {
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
