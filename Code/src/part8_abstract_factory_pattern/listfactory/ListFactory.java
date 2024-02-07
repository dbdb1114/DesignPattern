package part8_abstract_factory_pattern.listfactory;

import part8_abstract_factory_pattern.factory.Factory;
import part8_abstract_factory_pattern.factory.Link;
import part8_abstract_factory_pattern.factory.Page;
import part8_abstract_factory_pattern.factory.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title,author);
    }
}
