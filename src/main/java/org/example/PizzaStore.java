package org.example;

import java.util.*;

public class PizzaStore {

    public final static List<String> PIZZA_TYPE_LIST = new ArrayList<>(Arrays.asList(
            "Chicken", "Hawaii", "Pepperoni"));
    public final static Map<String, Double> PIZZA_SIZE_MAP = new HashMap<>();

    static {
        PIZZA_SIZE_MAP.put("Small", 10.00);
        PIZZA_SIZE_MAP.put("Medium", 15.00);
        PIZZA_SIZE_MAP.put("Large", 20.00);
    }
}
