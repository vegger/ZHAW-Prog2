package ch.zhaw.prog2.shoppinglist_product;

public interface Product {
    /**
     * Returns the unique id or the product.
     *
     * @return String unique Id of the product
     */
    String getProductId();

    /**
     * Returns the name of the product persented to the customer on the shopping list.
     *
     * @return String name of the product
     */
    String getName();

    /**
     * Returns the requested quantity of products in the shopping list.
     * Always represented in full units.
     *
     * @return int quantity of a product
     */
    int getQuantity();

    /**
     * Returns the price of a single unit of the product, provided by the price service.
     *
     * @return price of a single unit of the product.
     */
    double getPrice();
}
