package ch.zhaw.prog2.shoppinglist_product;

/**
 * Produkt, das gekauft werden kann. Es werden immer ganze Produkte gerechnet (Stk.)
 * z.B. 3 mal Kopfsalat oder 5 Liter Milch oder 2 Tafeln Schokolade
 *
 * @author bles
 * @version 1.0
 */
public class ProductMigros implements Product {
    private final String productId;
    private final String name;
    private final int quantity;
    private PriceService priceService = new PriceServiceMigros();

    public ProductMigros(String productId, String name, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity of a product must be greater or equals 0");
        }
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gibt den Preis zurück, den der PriceService für das Produkt zurückliefert
     *
     * @return double    Preis
     */
    @Override
    public double getPrice() {
        return priceService.getPrice(this);
    }

}
