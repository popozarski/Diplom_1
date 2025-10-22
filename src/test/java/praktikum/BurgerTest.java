package praktikum;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    private Burger burger;
    private Bun mockBun;
    private Ingredient mockIngredient1;
    private Ingredient mockIngredient2;

    @Before
    public void setUp() {
        burger = new Burger();
        mockBun = mock(Bun.class);
        mockIngredient1 = mock(Ingredient.class);
        mockIngredient2 = mock(Ingredient.class);
    }

    @Test
    public void testSetBunsAssignsBun() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    public void testAddIngredientIncreasesSize() {
        burger.addIngredient(mockIngredient1);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void testAddIngredientAddsCorrectIngredient() {
        burger.addIngredient(mockIngredient1);
        assertEquals(mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testAddMultipleIngredientsSize() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void testAddMultipleIngredientsOrder() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
    }

    @Test
    public void testRemoveIngredientReducesSize() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredientRemovesCorrectOne() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.removeIngredient(0);
        assertEquals(mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientSwapsPositions() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.moveIngredient(0, 1);
        assertEquals(mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientToBeginningReordersList() {
        Ingredient mockIngredient3 = mock(Ingredient.class);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        burger.moveIngredient(2, 0);
        assertEquals(mockIngredient3, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
    }

    @Test(expected = NullPointerException.class)
    public void testGetPriceThrowsWhenNoBun() {
        when(mockIngredient1.getPrice()).thenReturn(50f);
        burger.addIngredient(mockIngredient1);
        burger.getPrice();
    }

    @Test
    public void testGetPriceWithBunOnlyReturnsDoubleBunPrice() {
        when(mockBun.getPrice()).thenReturn(100f);
        burger.setBuns(mockBun);
        float price = burger.getPrice();
        assertEquals(200f, price, 0.01f);
    }

    @Test
    public void testGetPriceWithBunOnlyInvokesGetPriceOnBun() {
        when(mockBun.getPrice()).thenReturn(100f);
        burger.setBuns(mockBun);
        burger.getPrice();
        verify(mockBun, times(1)).getPrice();
    }

    @Test
    public void testGetPriceWithIngredientsAddsCorrectly() {
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getPrice()).thenReturn(75f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        float price = burger.getPrice();
        assertEquals(325f, price, 0.01f);
    }

    @Test
    public void testGetPriceWithIngredientsInvokesGetPriceOnAllComponents() {
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getPrice()).thenReturn(75f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.getPrice();
        verify(mockBun, times(1)).getPrice();
        verify(mockIngredient1, times(1)).getPrice();
        verify(mockIngredient2, times(1)).getPrice();
    }

    @Test
    public void testGetReceiptIncludesAllLines() {
        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getName()).thenReturn("hot sauce");
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getName()).thenReturn("cutlet");
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getPrice()).thenReturn(75f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= sauce hot sauce ="));
        assertTrue(receipt.contains("= filling cutlet ="));
        assertTrue(receipt.contains("Price: 325"));
    }

    @Test
    public void testGetReceiptInvokesGetNameOnAllComponents() {
        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getName()).thenReturn("hot sauce");
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient2.getName()).thenReturn("cutlet");
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getPrice()).thenReturn(75f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.getReceipt();
        verify(mockBun, times(2)).getName();
        verify(mockIngredient1, times(1)).getName();
        verify(mockIngredient2, times(1)).getName();
    }

    @Test
    public void testGetReceiptFormatLines() {
        when(mockBun.getName()).thenReturn("white bun");
        when(mockBun.getPrice()).thenReturn(50f);
        burger.setBuns(mockBun);
        String[] lines = burger.getReceipt().split(System.lineSeparator());
        assertEquals("(==== white bun ====)", lines[0]);
        assertEquals("(==== white bun ====)", lines[1]);
        assertTrue(lines[2].isEmpty());
        assertTrue(lines[3].startsWith("Price:"));
    }
}



