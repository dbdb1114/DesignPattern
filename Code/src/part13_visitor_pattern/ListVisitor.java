package part13_visitor_pattern;

public class ListVisitor extends Visitor {

    private String currentdir = "";

    @Override
    void visit(File file) {
        System.out.println(currentdir + "/" + file);
    }

    @Override
    void visit(Directory directory) {
        System.out.println(currentdir + "/" + directory);
        String savedir = currentdir;
        currentdir = currentdir + "/" + directory.getName();
        for (Entry entry : directory) {
            entry.accept(this);
        }
        currentdir = savedir;
    }
}
