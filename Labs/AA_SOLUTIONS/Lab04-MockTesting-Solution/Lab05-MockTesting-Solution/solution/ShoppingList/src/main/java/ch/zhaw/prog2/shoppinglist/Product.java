package ch.zhaw.prog2.shoppinglist;

/**
 * Produkt, das gekauft werden kann. Es werden immer ganze Produkte gerechnet (Stk.)
 * z.B. 3 mal Kopfsalat oder 5 Liter Milch oder 2 Tafeln Schokolade
 *
 * @author bles
 */
public class Product {
    private final String productId;
    private final String name;
    private final int quantity;

    public Product(String productId, String name, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity of a product must be greater or equals 0");
        }
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

}
