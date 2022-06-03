package ch.zhaw.prog2.shoppinglist;

public interface PriceService {
    /**
     * Looks up the price of a single unit of the given product from a specific vendor.
     *
     * @param product represents the product for which the price is requested
     * @return price of a single unit from a specific vendor
     */
    double getPrice(Product product);
}
