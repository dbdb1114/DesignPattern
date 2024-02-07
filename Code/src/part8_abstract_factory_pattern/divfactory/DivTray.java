package part8_abstract_factory_pattern.divfactory;

import part8_abstract_factory_pattern.factory.Tray;

public class DivTray extends Tray {
    public DivTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHTML() {
        return null;
    }
}
