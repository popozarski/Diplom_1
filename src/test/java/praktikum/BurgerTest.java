package praktikum;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса Burger без параметризации
 */
public class BurgerTest {

    private Burger burger;
    private Bun mockBun;
    private Ingredient mockIngredient1;
    private Ingredient mockIngredient2;

    @Before
    public void setUp() {
        burger = new Burger();

        // Создаем моки для Bun и Ingredient
        mockBun = mock(Bun.class);
        mockIngredient1 = mock(Ingredient.class);
        mockIngredient2 = mock(Ingredient.class);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(mockIngredient1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testAddMultipleIngredients() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        assertEquals(2, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.moveIngredient(0, 1);

        assertEquals(mockIngredient2, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
    }

    @Test
    public void testMoveIngredientToBeginning() {
        Ingredient mockIngredient3 = mock(Ingredient.class);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(2, 0);

        assertEquals(mockIngredient3, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
        assertEquals(mockIngredient2, burger.ingredients.get(2));
    }

    @Test
    public void testGetPriceWithNoBun() {
        when(mockIngredient1.getPrice()).thenReturn(50f);
        burger.addIngredient(mockIngredient1);

        // Проверяем, что метод getPrice вызывается на ингредиентах
        try {
            burger.getPrice();
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Ожидается исключение, так как bun == null
        }
    }

    @Test
    public void testGetPriceWithBunOnly() {
        when(mockBun.getPrice()).thenReturn(100f);
        burger.setBuns(mockBun);

        float price = burger.getPrice();

        assertEquals(200f, price, 0.01f);
        verify(mockBun, times(1)).getPrice();
    }

    @Test
    public void testGetPriceWithBunAndIngredients() {
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getPrice()).thenReturn(75f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        float price = burger.getPrice();

        assertEquals(325f, price, 0.01f); // 100*2 + 50 + 75
        verify(mockBun, times(1)).getPrice();
        verify(mockIngredient1, times(1)).getPrice();
        verify(mockIngredient2, times(1)).getPrice();
    }

    @Test
    public void testGetReceiptWithBunAndIngredients() {
        IngredientType mockType1 = IngredientType.SAUCE;
        IngredientType mockType2 = IngredientType.FILLING;

        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getName()).thenReturn("hot sauce");
        when(mockIngredient1.getType()).thenReturn(mockType1);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getName()).thenReturn("cutlet");
        when(mockIngredient2.getType()).thenReturn(mockType2);
        when(mockIngredient2.getPrice()).thenReturn(75f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= sauce hot sauce ="));
        assertTrue(receipt.contains("= filling cutlet ="));
        assertTrue(receipt.contains("Price: 325"));

        verify(mockBun, times(2)).getName();
        verify(mockIngredient1, times(1)).getType();
        verify(mockIngredient1, times(1)).getName();
        verify(mockIngredient2, times(1)).getType();
        verify(mockIngredient2, times(1)).getName();
    }

    @Test
    public void testGetReceiptFormat() {
        when(mockBun.getName()).thenReturn("white bun");
        when(mockBun.getPrice()).thenReturn(50f);

        burger.setBuns(mockBun);

        String receipt = burger.getReceipt();

        //String[] lines = receipt.split("\n");
        String[] lines = receipt.split(System.lineSeparator());
        assertEquals("(==== white bun ====)", lines[0]);
        assertEquals("(==== white bun ====)", lines[1]);
        assertTrue(lines[2].isEmpty());
        assertTrue(lines[3].startsWith("Price:"));
    }
}


