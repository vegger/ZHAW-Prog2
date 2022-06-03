package ch.zhaw.prog2.shoppinglist;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private final List<Product> products = new ArrayList<>();
    private PriceService priceService;

    public PriceService getPriceService() {
        return priceService;
    }

    public void setPriceService(PriceService priceService) {
        this.priceService = priceService;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalCosts() {
        double costs = 0.0;
        for (Product product : products) {
            costs += priceService.getPrice(product) * product.getQuantity();
        }
        return costs;
    }
}
