package part6_prototype_pattern;

import part6_prototype_pattern.framework.Product;

public class UnderlinePen extends Product {
    private String ulchar;

    public UnderlinePen(String ulchar) {
        this.ulchar = ulchar;
    }

    @Override
    public void use(String s) {
        int ulen = s.length();
        System.out.println(s);
        for (int i = 0; i < ulen; i++) {
            System.out.print(ulchar);
        }
        System.out.println();
    }
}
