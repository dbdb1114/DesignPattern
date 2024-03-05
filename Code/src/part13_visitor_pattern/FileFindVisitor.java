package part13_visitor_pattern;

import java.util.ArrayList;

public class FileFindVisitor extends Visitor{

    private String findExt = "";
    private String currentdir = "";
    private ArrayList<File> foundFile = new ArrayList<>();

    public FileFindVisitor(String findExt) {
        this.findExt = findExt;
    }

    public ArrayList<File> getFoundFile() {
        return foundFile;
    }

    @Override
    void visit(File file) {
        int st = file.getName().indexOf('.');
        if(st > 0 && file.getName().substring(st).equals(findExt)){
            foundFile.add(file);
        }
    }

    @Override
    void visit(Directory directory) {
        String savedir = currentdir;
        currentdir = currentdir + "/" + directory.getName();
        for (Entry entry : directory) {
            entry.accept(this);
        }
        currentdir = savedir;
    }
}
