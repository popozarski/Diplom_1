package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


 //Параметризованные тесты для метода moveIngredient

@RunWith(Parameterized.class)
public class BurgerMoveIngredientParameterizedTest {

    @Parameterized.Parameter(0)
    public int fromIndex;

    @Parameterized.Parameter(1)
    public int toIndex;

    @Parameterized.Parameter(2)
    public int expectedPositionOfFirstIngredient;

    @Parameterized.Parameters(name = "Move from {0} to {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 1, 1},  // Перемещение с позиции 0 на позицию 1
                {1, 0, 0},  // Перемещение с позиции 1 на позицию 0
                {0, 2, 2},  // Перемещение в конец
                {2, 0, 0},  // Перемещение из конца в начало
        });
    }

    @Test
    public void testMoveIngredient() {
        Burger burger = new Burger();
        Ingredient ingredient1 = mock(Ingredient.class);
        Ingredient ingredient2 = mock(Ingredient.class);
        Ingredient ingredient3 = mock(Ingredient.class);

        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        burger.moveIngredient(fromIndex, toIndex);

        if (fromIndex == 0) {
            assertEquals(ingredient1, burger.ingredients.get(expectedPositionOfFirstIngredient));
        }
    }
}
