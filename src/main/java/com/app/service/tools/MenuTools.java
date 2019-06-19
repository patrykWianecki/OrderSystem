package com.app.service.tools;

public class MenuTools {

    public static void showAvailableOperations(String name) {
        System.out.println("Choose operation:");
        System.out.println("1 - Add new " + name);
        System.out.println("2 - Delete " + name);
        System.out.println("3 - Update " + name);
        System.out.println("4 - Show all elements from " + name);
        System.out.println("5 - Show chosen " + name);
        System.out.println("0 - Go back");
    }
}
