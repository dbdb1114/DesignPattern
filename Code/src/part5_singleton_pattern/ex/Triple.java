package part5_singleton_pattern.ex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.UnknownElementException;

public class Triple {
    private static Map<String, Triple> map = new HashMap<>();
    static {
        String[] names = {"ALPHA", "BETA", "GAMA"};
        Arrays.stream(names).forEach(name -> map.put(name, new Triple(name)));
    }

    private Triple(String name) {
        System.out.println("The instance "+name + " is created.");
        this.name = name;
    }
    private String name;
    public static Triple getInstance(String name){
        return map.get(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
