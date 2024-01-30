package part4_factory_method_pattern.id_card_factory;

import java.util.HashMap;
import java.util.Map;
import part4_factory_method_pattern.framework.Factory;
import part4_factory_method_pattern.framework.Product;

public class IDCardFactory extends Factory {

    static private Map<Integer, String> idCardTable = new HashMap<>();
    static private int cardNo = 0;

    @Override
    public IDCard createProduct(String owner) {
        idCardTable.put(cardNo,owner);
        return new IDCard(owner, ++cardNo);
    }

    @Override
    public void registerProduct(Product product) {
        System.out.println(product+"을 등록했습니다.");
    }
}
