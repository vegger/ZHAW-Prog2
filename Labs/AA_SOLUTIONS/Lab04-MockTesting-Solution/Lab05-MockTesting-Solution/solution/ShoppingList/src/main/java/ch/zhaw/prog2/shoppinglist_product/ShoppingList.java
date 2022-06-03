package ch.zhaw.prog2.shoppinglist_product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalCosts() {
        double costs = 0.0;
        for (Product product : products) {
            costs += product.getPrice() * product.getQuantity();
        }
        return costs;
    }
}
