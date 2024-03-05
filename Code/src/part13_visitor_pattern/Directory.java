package part13_visitor_pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Directory extends Entry implements Iterable<Entry> {
    private String name;
    private List<Entry> directory = new ArrayList<>();
    private String path="";

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return directory.stream().mapToInt(Entry::getSize).sum();
    }

    @Override
    public void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        for (Entry entry : directory) {
            entry.printList(prefix+"/" + name);
        }
    }

    @Override
    public String getPath() {
        return path+"/"+name;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public Entry add(Entry entry){
        directory.add(entry);
        String path = getPath() + "/" + entry.getName();
        entry.setPath(path);
        return this;
    }

    @Override
    public Iterator<Entry> iterator() {
        return directory.iterator();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
