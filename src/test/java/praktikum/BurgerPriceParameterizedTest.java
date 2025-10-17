package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Параметризованные тесты для метода getPrice
 */
@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    @Parameterized.Parameter(0)
    public float bunPrice;

    @Parameterized.Parameter(1)
    public float[] ingredientPrices;

    @Parameterized.Parameter(2)
    public float expectedPrice;

    @Parameterized.Parameters(name = "Bun price: {0}, Ingredients: {1}, Expected: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {100f, new float[]{}, 200f},                          // Только булочки
                {100f, new float[]{50f}, 250f},                       // Булочки + 1 ингредиент
                {100f, new float[]{50f, 75f}, 325f},                  // Булочки + 2 ингредиента
                {100f, new float[]{50f, 75f, 25f}, 350f},             // Булочки + 3 ингредиента
                {50f, new float[]{10f, 20f, 30f}, 160f},              // Дешевые компоненты
                {200f, new float[]{100f, 150f, 200f}, 850f},          // Дорогие компоненты
                {0f, new float[]{0f, 0f}, 0f},                        // Все бесплатно
        });
    }

    @Test
    public void testGetPrice() {
        Burger burger = new Burger();
        Bun mockBun = mock(Bun.class);
        when(mockBun.getPrice()).thenReturn(bunPrice);

        burger.setBuns(mockBun);

        for (float ingredientPrice : ingredientPrices) {
            Ingredient mockIngredient = mock(Ingredient.class);
            when(mockIngredient.getPrice()).thenReturn(ingredientPrice);
            burger.addIngredient(mockIngredient);
        }

        float actualPrice = burger.getPrice();

        assertEquals(expectedPrice, actualPrice, 0.01f);
    }
}
