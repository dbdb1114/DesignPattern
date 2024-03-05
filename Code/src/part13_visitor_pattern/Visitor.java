package part13_visitor_pattern;

public abstract class Visitor {
    abstract void visit(File file);
    abstract void visit(Directory directory);

}
