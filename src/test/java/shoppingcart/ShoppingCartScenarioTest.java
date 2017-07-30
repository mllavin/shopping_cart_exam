package shoppingcart;

import shoppingcart.helper.TestHelper;
import shoppingcart.interfaces.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import shoppingcart.sample.SampleItem;
import shoppingcart.sample.SamplePricingRules;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class ShoppingCartScenarioTest {

    private static final List<Item> SCENARIO_1_ITEMS_ADDED = TestHelper.generateSampleItems(3, 0, 1, 0);
    private static final List<Item> SCENARIO_1_EXPECTED_ITEMS = TestHelper.generateSampleItems(3, 0, 1, 0);
    private static final List<Item> SCENARIO_2_ITEMS_ADDED = TestHelper.generateSampleItems(2, 0, 4, 0);
    private static final List<Item> SCENARIO_2_EXPECTED_ITEMS = TestHelper.generateSampleItems(2, 0, 4, 0);
    private static final List<Item> SCENARIO_3_ITEMS_ADDED = TestHelper.generateSampleItems(1, 2, 0, 0);
    private static final List<Item> SCENARIO_3_EXPECTED_ITEMS = TestHelper.generateSampleItems(1, 2, 0, 2);
    private static final List<Item> SCENARIO_4_ITEMS_ADDED = TestHelper.generateSampleItems(1, 0, 0, 1);
    private static final List<Item> SCENARIO_4_EXPECTED_ITEMS = TestHelper.generateSampleItems(1, 0, 0, 1);
    private static final String NO_CODE = null;

    @Parameterized.Parameter
    public String scenarioName;
    @Parameterized.Parameter(1)
    public List<SampleItem> itemsToAdd;
    @Parameterized.Parameter(2)
    public String promoCode;
    @Parameterized.Parameter(3)
    public String expectedCartTotal;
    @Parameterized.Parameter(4)
    public List<SampleItem> expectedCartItems;

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> scenarios() {
        return Arrays.asList(new Object[][] {
            {"Scenario 1",  SCENARIO_1_ITEMS_ADDED,     NO_CODE,        "94.70",    SCENARIO_1_EXPECTED_ITEMS},
            {"Scenario 2",  SCENARIO_2_ITEMS_ADDED,     NO_CODE,        "209.40",   SCENARIO_2_EXPECTED_ITEMS},
            {"Scenario 3",  SCENARIO_3_ITEMS_ADDED,     NO_CODE,        "84.70",    SCENARIO_3_EXPECTED_ITEMS},
            {"Scenario 4",  SCENARIO_4_ITEMS_ADDED,     "I<3AMAYSIM",   "31.32",    SCENARIO_4_EXPECTED_ITEMS},
        });
    }

    private ShoppingCart cart;

    @Before
    public void setUp() {
        cart = new ShoppingCart(new SamplePricingRules());

        boolean isPromoCodeAdded = false;
        for (SampleItem product: itemsToAdd) {
            if (promoCode == null || isPromoCodeAdded) {
                cart.add(product);
            }
            else {
                cart.add(product, promoCode);
                isPromoCodeAdded = true;
            }
        }
    }

    @Test
    public void shouldHaveCorrectTotal() {
        String actualTotal = TestHelper.formatDecimalValueToString(cart.total());

        assertEquals(expectedCartTotal, actualTotal);
    }

    @Test
    public void shouldHaveCorrectCartItems() {
        assertEquals(expectedCartItems.size(), cart.items().size());
        assertTrue(expectedCartItems.containsAll(cart.items()));
    }

}
