package part8_abstract_factory_pattern.divfactory;

import part8_abstract_factory_pattern.factory.Link;

public class DivLink extends Link {
    public DivLink(String caption, String url) {
        super(caption, url);
    }

    @Override
    public String makeHTML() {
        return null;
    }
}
