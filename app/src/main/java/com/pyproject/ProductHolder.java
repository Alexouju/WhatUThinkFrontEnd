package com.pyproject;

import java.util.Map;

public class ProductHolder {
    private Map<String, Product> productMap;

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Product> productMap) {
        this.productMap = productMap;
    }
}
