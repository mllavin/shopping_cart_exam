package shoppingcart;

import shoppingcart.interfaces.Item;
import shoppingcart.interfaces.PricingRules;
import shoppingcart.sample.SampleItem;
import shoppingcart.sample.SamplePricingRules;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DecimalFormat FORMATTER = new DecimalFormat("#0.00");
    private static final String SPACER = "==============================";
    private static final String NO_ITEMS = "(No items)";
    private static final String INPUT_ANY_OTHER_KEY_TO_CANCEL = "Input any other key to cancel";
    private static final String CURRENCY = "$";
    private static final String OPTION_ADD_ITEM = "1";
    private static final String OPTION_ADD_ITEM_WITH_PROMO_CODE = "2";
    private static final String OPTION_RESET_CART = "3";
    private static final String OPTION_CLOSE_PROGRAM = "exit";

    private PricingRules pricingRules;
    private ShoppingCart cart;

    public static void main(String[] args) {
        Main ui = new Main();

        while (true) {
            System.out.println(SPACER);
            ui.displayCart();
            ui.displayOptions();
            int selectedItem;
            String option = ui.getNextStringInput("What do you want to do?");
            System.out.println(SPACER);
            switch(option.toLowerCase()) {
                case OPTION_ADD_ITEM:
                    ui.displayAvailableItemsToAdd();
                    selectedItem = ui.getNextIntegerInput("Which item do you want to add?");
                    ui.addSelectedItemToCart(selectedItem);
                    break;
                case OPTION_ADD_ITEM_WITH_PROMO_CODE:
                    ui.displayWarningThatPromoCodeWillBeOverriden();
                    ui.displayAvailableItemsToAdd();
                    selectedItem = ui.getNextIntegerInput("Which item do you want to add?");
                    String promoCode = ui.getNextStringInput("What is the promo code?");
                    ui.addSelectedItemToCart(selectedItem, promoCode);
                    break;
                case OPTION_RESET_CART:
                    ui.resetCart();
                    break;
                case OPTION_CLOSE_PROGRAM:
                    ui.closeProgram();
                    break;
            }
        }
    }

    private Main() {
        pricingRules = new SamplePricingRules();
        cart = new ShoppingCart(pricingRules);
    }

    private void displayCart() {
        System.out.println("Your Shopping Cart:");
        Map<String, Integer> cartItems = convertCartItemsListToMap();
        if (cartItems.size() > 0) {
            for (String itemName: cartItems.keySet()) {
                System.out.println(cartItems.get(itemName) + " x " + itemName);
            }
            if (cart.getAppliedPromoCode() != null) {
                System.out.println("'" + cart.getAppliedPromoCode() + "' Promo Applied");
            }
            System.out.println("TOTAL" + "\t\t\t\t\t" + CURRENCY + FORMATTER.format(cart.total()));
        }
        else {
            System.out.println(NO_ITEMS);
        }
        System.out.println(SPACER);
    }

    private Map<String, Integer> convertCartItemsListToMap() {
        Map<String, Integer> cartItems = new TreeMap<>();
        for (Item item: cart.items()) {
            String name = item.getName();
            int quantity = 0;
            if (cartItems.containsKey(name)) {
                quantity = cartItems.get(name);
            }
            cartItems.put(name, quantity + 1);
        }
        return cartItems;
    }

    private void displayOptions() {
        System.out.println("Options:");
        System.out.println(OPTION_ADD_ITEM + ") Add item ");
        System.out.println(OPTION_ADD_ITEM_WITH_PROMO_CODE + ") Add item with promo code");
        System.out.println(OPTION_RESET_CART + ") Reset cart");
        System.out.println("Input '" + OPTION_CLOSE_PROGRAM + "' to close program");
        System.out.println(SPACER);
    }

    private String getNextStringInput(String message) {
        System.out.print(message.trim() + " ");
        return SCANNER.next();
    }

    private int getNextIntegerInput(String message) {
        System.out.print(message.trim() + " ");
        try {
            return Integer.parseInt(SCANNER.next());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    private void displayAvailableItemsToAdd() {
        System.out.println("Items that can be added: ");
        int availableItems = SampleItem.values().length;
        if (availableItems > 0) {
            for (int i = 1; i <= availableItems; i++) {
                SampleItem product = SampleItem.values()[i - 1];
                System.out.println(i + ". " + product.getName() + " (" + CURRENCY + product.getPrice() + ")");
            }
            System.out.println(INPUT_ANY_OTHER_KEY_TO_CANCEL);
        }
        else {
            System.out.println(NO_ITEMS);
        }
        System.out.println(SPACER);
    }

    private void addSelectedItemToCart(int selectedItem) {
        addSelectedItemToCart(selectedItem, null);
    }

    private void addSelectedItemToCart(int selectedItem, String promoCode) {
        Item[] items = SampleItem.values();
        if (selectedItem > 0 && selectedItem <= items.length) {
            if (promoCode == null) {
                cart.add(items[selectedItem - 1]);
            }
            else {
                cart.add(items[selectedItem - 1], promoCode);
                if (!promoCode.equals(cart.getAppliedPromoCode())) {
                    System.out.println("- Promo code invalid!");
                }
            }
        }
    }

    private void resetCart() {
        cart = new ShoppingCart(pricingRules);
    }

    private void displayWarningThatPromoCodeWillBeOverriden() {
        System.out.println("Warning: Applied promo will be overwritten");
    }

    private void closeProgram() {
        System.out.println("- Closing program...");
        System.exit(0);
    }

}
