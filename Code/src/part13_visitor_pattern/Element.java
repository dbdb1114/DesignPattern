package part13_visitor_pattern;

public interface Element {
    void accept(Visitor v);
}
