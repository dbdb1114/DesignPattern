package part8_abstract_factory_pattern.divfactory;

import part8_abstract_factory_pattern.factory.Factory;
import part8_abstract_factory_pattern.factory.Link;
import part8_abstract_factory_pattern.factory.Page;
import part8_abstract_factory_pattern.factory.Tray;

public class DivFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new DivLink(caption,url);
    }

    @Override
    public Tray createTray(String caption) {
        return new DivTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new DivPage(title, author);
    }
}
