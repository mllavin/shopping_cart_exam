package shoppingcart.sample;

import shoppingcart.helper.TestHelper;
import shoppingcart.interfaces.Item;
import shoppingcart.interfaces.PricingRules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SamplePricingRulesScenarioTest {

    private static final List<Item> NO_ITEMS = TestHelper.generateSampleItems(0, 0, 0, 0);
    private static final List<Item> ONE_UNLIMITED_1GB = TestHelper.generateSampleItems(1, 0, 0, 0);
    private static final List<Item> THREE_UNLIMITED_1GB = TestHelper.generateSampleItems(3, 0, 0, 0);
    private static final List<Item> ONE_UNLIMITED_2GB = TestHelper.generateSampleItems(0, 1, 0, 0);
    private static final List<Item> ONE_UNLIMITED_5GB = TestHelper.generateSampleItems(0, 0, 1, 0);
    private static final List<Item> FOUR_UNLIMITED_5GB = TestHelper.generateSampleItems(0, 0, 4, 0);
    private static final List<Item> ONE_1GB_DATA_PACK = TestHelper.generateSampleItems(0, 0, 0, 1);
    private static final List<Item> TEN_1GB_DATA_PACK = TestHelper.generateSampleItems(0, 0, 0, 10);
    private static final List<Item> ONE_OF_ALL_ITEMS = TestHelper.generateSampleItems(1, 1, 1, 1);
    private static final List<Item> TEN_OF_ALL_ITEMS = TestHelper.generateSampleItems(10, 10, 10, 10);
    private static final String NO_CODE = null;

    @Parameter
    public String scenarioName;
    @Parameter(1)
    public List<Item> items;
    @Parameter(2)
    public String expectedPrice;
    @Parameter(3)
    public String expectedPerItemDiscount;
    @Parameter(4)
    public String promoCode;
    @Parameter(5)
    public String expectedPromoCodeDiscount;
    @Parameter(6)
    public List<Item> expectedFreeBundles;

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> scenarios() {
        return Arrays.asList(new Object[][] {
            {"No items",
                NO_ITEMS,               "0.00",     "0.00",     NO_CODE,        "0.00",     NO_ITEMS},
            {"1 Unlimited 1GB",
                ONE_UNLIMITED_1GB,      "24.90",    "0.00",     NO_CODE,        "0.00",     NO_ITEMS},
            {"3 Unlimited 1GB",
                THREE_UNLIMITED_1GB,    "74.70",    "24.90",    NO_CODE,        "0.00",     NO_ITEMS},
            {"1 Unlimited 2GB",
                ONE_UNLIMITED_2GB,      "29.90",    "0.00",     NO_CODE,        "0.00",     ONE_1GB_DATA_PACK},
            {"1 Unlimited 5GB",
                ONE_UNLIMITED_5GB,      "44.90",    "0.00",     NO_CODE,        "0.00",     NO_ITEMS},
            {"4 Unlimited 5GB",
                FOUR_UNLIMITED_5GB,     "179.60",   "20.00",    NO_CODE,        "0.00",     NO_ITEMS},
            {"1 1GB Data Pack",
                ONE_1GB_DATA_PACK,      "9.90",     "0.00",     NO_CODE,        "0.00",     NO_ITEMS},
            {"1 of all items",
                ONE_OF_ALL_ITEMS,       "109.60",   "0.00",     NO_CODE,        "0.00",     ONE_1GB_DATA_PACK},
            {"10 of all items",
                TEN_OF_ALL_ITEMS,       "1096.00",  "124.70",   NO_CODE,        "0.00",     TEN_1GB_DATA_PACK},
            {"10 of all items with code I<3AMAYSIM",
                TEN_OF_ALL_ITEMS,       "1096.00",  "124.70",   "I<3AMAYSIM",   "97.13",    TEN_1GB_DATA_PACK},
            {"10 of all items with invalid code",
                TEN_OF_ALL_ITEMS,       "1096.00",  "124.70",   "AUGUST2017",    "0.00",    TEN_1GB_DATA_PACK},
        });
    }

    private PricingRules rules;

    @Before
    public void setUp() {
        rules = new SamplePricingRules();
    }

    @Test
    public void shouldCalculatePriceWithoutDiscount() {
        String actualPrice = TestHelper.formatDecimalValueToString(rules.calculatePriceWithoutDiscount(items));

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void shouldCalculatePerItemDiscount() {
        String actualPerItemDiscount = TestHelper.formatDecimalValueToString(rules.calculatePerItemDiscount(items));

        assertEquals(expectedPerItemDiscount, actualPerItemDiscount);
    }

    @Test
    public void shouldCalculatePromoCodeDiscount() {
        String actualPromoCodeDiscount = TestHelper.formatDecimalValueToString(rules.calculatePromoCodeDiscount(items, promoCode));

        assertEquals(expectedPromoCodeDiscount, actualPromoCodeDiscount);
    }

    @Test
    public void shouldCalculateFreeBundles() {
        assertEquals(expectedFreeBundles, rules.calculateFreeBundles(items));
    }

}
