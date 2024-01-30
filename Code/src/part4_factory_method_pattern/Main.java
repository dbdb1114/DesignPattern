package part4_factory_method_pattern;

import part4_factory_method_pattern.id_card_factory.IDCardFactory;
import part4_factory_method_pattern.framework.Factory;
import part4_factory_method_pattern.framework.Product;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("Youngjin Kim");
        Product card2 = factory.create("Heungmin Son");
        Product card3 = factory.create("Kane");

        card1.use();
        card2.use();
        card3.use();
    }
}
