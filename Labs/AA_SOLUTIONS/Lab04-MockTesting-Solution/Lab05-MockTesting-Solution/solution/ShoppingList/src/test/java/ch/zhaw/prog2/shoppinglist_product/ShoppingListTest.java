package ch.zhaw.prog2.shoppinglist_product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Testen der Methode getTotalCosts aus der Klasse Shopping
 *
 * @author bles
 */
class ShoppingListTest {
    // 👨‍🏫 Beginn ML
    private static final double MILK_PRICE = 1.85;
    private static final int MILK_QUANTITY = 4;
    private static final double SALAD_PRICE = 2.4;
    private static final int SALAD_QUANTITY = 1;
    // 👨‍🏫 Ende ML

    private ShoppingList shoppingList;
    // Ansatz: Die Produkte selbst mocken (für testGetTotalCostsWithProductMock())
    // PriceService wird nicht mehr verwendet
    // 👨‍🏫 Beginn ML
    @Mock
    private ProductMigros milkMock;
    @Mock
    private ProductMigros saladMock;
    // 👨‍🏫 Ende ML

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingList = new ShoppingList();
    }

    @Test
    void testGetTotalCostsWithProductMock() {
        // 👨‍🏫 Beginn ML
        shoppingList.addProduct(milkMock);
        shoppingList.addProduct(saladMock);
        when(milkMock.getPrice()).thenReturn(MILK_PRICE);
        when(milkMock.getQuantity()).thenReturn(MILK_QUANTITY);
        when(saladMock.getPrice()).thenReturn(SALAD_PRICE);
        when(saladMock.getQuantity()).thenReturn(SALAD_QUANTITY);
        double expectedCost = MILK_PRICE * MILK_QUANTITY + SALAD_PRICE * SALAD_QUANTITY;
        assertEquals(expectedCost, shoppingList.getTotalCosts(), 0.01, "Total costs don't match the expected value.");
        // 👨‍🏫 Ende ML
    }


}
