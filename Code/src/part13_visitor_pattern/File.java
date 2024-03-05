package part13_visitor_pattern;

public class File extends Entry {
    private String path;
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void printList(String prefix) {
        System.out.println(prefix+"/"+this);
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath(){
        return path+"/"+name;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
