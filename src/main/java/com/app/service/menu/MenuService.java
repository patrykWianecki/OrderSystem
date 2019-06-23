package com.app.service.menu;

import com.app.model.State;

import java.util.Scanner;

import static com.app.model.State.INIT;

public class MenuService {

    private static Scanner scanner = new Scanner(System.in);
    private static State state = State.INIT;

    public void start() {
        menu();
    }

    private void menu() {
        while (state != State.EXIT) {
            switch (state) {
                case INIT: {
                    printInit();
                    break;
                }
                case CATEGORY: {
                    state = new CategoryMenu().printAvailableOperationsOnCategories();
                    break;
                }
                case COUNTRY: {
                    state = new CountryMenu().printAvailableOperationsOnCountries();
                    break;
                }
                case CUSTOMER: {
                    state = new CustomerMenu().printAvailableOperationsOnCustomers();
                    break;
                }
                case PRODUCER: {
                    state = new ProducerMenu().printAvailableOperationsOnProducers();
                    break;
                }
                case PRODUCT: {
                    state = new ProductMenu().printAvailableOperationsOnProducts();
                    break;
                }
                case ORDER: {
                    state = new OrderMenu().printAvailableOperationsOnOrders();
                    break;
                }
                case OTHER: {
                    state = new OtherMenu().printOtherAvailableOperations();
                    break;
                }
            }
        }
    }

    private State printInit() {
        System.out.println("Choose operation on:");
        System.out.println("1 - Category");
        System.out.println("2 - Country");
        System.out.println("3 - Customer");
        System.out.println("4 - Producer");
        System.out.println("5 - Product");
        System.out.println("6 - Orders");
        System.out.println("7 - Other");
        System.out.println("0 - Exit");

        switch (scanner.nextInt()) {
            case 1: {
                state = State.CATEGORY;
                scanner.nextLine();
                break;
            }
            case 2: {
                state = State.COUNTRY;
                scanner.nextLine();
                break;
            }
            case 3: {
                state = State.CUSTOMER;
                scanner.nextLine();
                break;
            }
            case 4: {
                state = State.PRODUCER;
                scanner.nextLine();
                break;
            }
            case 5: {
                state = State.PRODUCT;
                scanner.nextLine();
                break;
            }
            case 6: {
                state = State.ORDER;
                scanner.nextLine();
                break;
            }
            case 7: {
                state = State.OTHER;
                scanner.nextLine();
                break;
            }
            case 0: {
                System.out.println("CIAO");
                state = State.EXIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = INIT;
                scanner.nextLine();
                break;
            }
        }
        return state;
    }
}
