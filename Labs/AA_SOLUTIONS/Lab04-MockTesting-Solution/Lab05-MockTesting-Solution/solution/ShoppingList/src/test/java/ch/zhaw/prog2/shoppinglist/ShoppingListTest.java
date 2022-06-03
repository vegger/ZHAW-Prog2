package ch.zhaw.prog2.shoppinglist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingListTest {
    // 👨‍🏫 Beginn ML
    private static final double MILK_PRICE = 1.85;
    private static final int MILK_QUANTITY = 3;
    private static final double SALAD_PRICE = 2.4;
    private static final int SALAD_QUANTITY = 2;
    // 👨‍🏫 Ende ML

    private final Product milk = new Product("MilkId", "Milk", MILK_QUANTITY);
    private final Product salad = new Product("SaladId", "Salad", SALAD_QUANTITY);
    private ShoppingList shoppingList;
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        // 👨‍🏫 Beginn ML
        shoppingList = new ShoppingList();
        priceService = mock(PriceService.class);
        shoppingList.setPriceService(priceService);
        // 👨‍🏫 Ende ML
    }

    @Test
    void testGetProducts() {
        // 👨‍🏫 Beginn ML
        addMilkAndSaladToShoppingList();
        List<Product> found = shoppingList.getProducts();
        assertNotNull(found, "we added products to the shopping list");
        List<Product> expected = List.of(milk, salad);
        assertTrue(found.containsAll(expected), "pricelist misses a least one product");
        assertTrue(expected.containsAll(found), "there are unexpected products in the pricelist");
        // 👨‍🏫 Ende ML
    }

    @Test
    void testAddProduct() {
        // 👨‍🏫 Beginn ML
        assertTrue(shoppingList.getProducts().isEmpty(), "we want to start with an empty productlist");
        shoppingList.addProduct(milk);
        assertEquals(milk, shoppingList.getProducts().get(0), "Not the expected product.");
        // 👨‍🏫 Ende ML
    }

    @Test
    void testGetTotalCosts() {
        // 👨‍🏫 Beginn ML
        addMilkAndSaladToShoppingList();
        when(priceService.getPrice(milk)).thenReturn(MILK_PRICE);
        when(priceService.getPrice(salad)).thenReturn(SALAD_PRICE);
        double expectedCosts = MILK_PRICE * MILK_QUANTITY + SALAD_PRICE * SALAD_QUANTITY;
        assertEquals(expectedCosts, shoppingList.getTotalCosts(), 0.01, "Calculation of costs not ok.");
        verify(priceService, times(2)).getPrice(any(Product.class));
        // 👨‍🏫 Ende ML
    }

    private void addMilkAndSaladToShoppingList() {
    	shoppingList.addProduct(milk);
        shoppingList.addProduct(salad);
    }

}
