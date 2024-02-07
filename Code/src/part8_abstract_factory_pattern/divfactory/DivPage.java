package part8_abstract_factory_pattern.divfactory;

import part8_abstract_factory_pattern.factory.Page;

public class DivPage extends Page {
    public DivPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        return null;
    }
}
