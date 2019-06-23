package com.app.service.menu;

import com.app.model.Customer;
import com.app.model.State;
import com.app.repository.customer.CustomerRepository;
import com.app.repository.customer.CustomerRepositoryImpl;
import com.app.service.tools.MenuTools;

import java.util.Comparator;
import java.util.Scanner;

import static com.app.model.State.CUSTOMER;
import static com.app.model.State.INIT;
import static com.app.service.tools.MenuTools.showAvailableOperations;

class CustomerMenu {

    private static final String TABLE_NAME = "customer";

    private static Scanner scanner = new Scanner(System.in);
    private static State state;

    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private MenuTools menuTools = new MenuTools();

    State printAvailableOperationsOnCustomers() {
        showAvailableOperations(TABLE_NAME);

        switch (scanner.nextInt()) {
            case 1: {
                state = printAddCustomer();
                break;
            }
            case 2: {
                state = printDeleteCustomer();
                break;
            }
            case 3: {
                state = printUpdateCustomer();
                break;
            }
            case 4: {
                state = printShowAllCustomers();
                break;
            }
            case 5: {
                state = printChosenCustomer();
                break;
            }
            case 0: {
                state = INIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = CUSTOMER;
                scanner.nextLine();
                break;
            }
        }
        return state;
    }

    private State printAddCustomer() {
        System.out.println("Enter Customer name:");
        String name = scanner.nextLine();

        System.out.println("Enter Customer surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter Customer age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        menuTools.showAvailableCountries();
        System.out.println("Choose country id:");
        long countryId = scanner.nextLong();
        scanner.nextLine();

        customerRepository.add(Customer
            .builder()
            .name(name)
            .surname(surname)
            .age(age)
            .countryId(countryId)
            .build()
        );
        state = CUSTOMER;
        return state;
    }

    private State printDeleteCustomer() {
        System.out.println("Choose Customer id from list to delete:");
        menuTools.showCustomersSortedById();
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = CUSTOMER;
            return state;
        } else {
            customerRepository.delete(choice);
            state = CUSTOMER;
            return state;
        }
    }

    private State printUpdateCustomer() {
        customerRepository
            .findAll()
            .forEach(System.out::println);

        System.out.println("Choose id:");
        long choice = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter new Customer name:");
        String name = scanner.nextLine();

        System.out.println("Enter new Customer surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter new Customer age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new Customer country id:");
        long countryId = scanner.nextLong();
        scanner.nextLine();

        customerRepository.update(Customer
            .builder()
            .id(choice)
            .name(name)
            .surname(surname)
            .age(age)
            .countryId(countryId)
            .build()
        );

        state = CUSTOMER;
        return state;
    }

    private State printShowAllCustomers() {
        System.out.println("1 - show all Customers sorted by name");
        System.out.println("2 - show all Customers sorted by id");
        System.out.println("0 - go back");

        switch (scanner.nextInt()) {
            case 1: {
                printCustomersSortedByName();
                break;
            }
            case 2: {
                printCustomersSortedById();
                break;
            }
            case 0: {
                state = CUSTOMER;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = CUSTOMER;
                break;
            }
        }

        return state;
    }

    private State printCustomersSortedById() {
        menuTools.showCustomersSortedById();
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = CUSTOMER;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = CUSTOMER;
        }
        return state;
    }

    private State printCustomersSortedByName() {
        customerRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Customer::getName))
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = CUSTOMER;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = CUSTOMER;
        }
        return state;
    }

    private State printChosenCustomer() {
        System.out.println("1 - show Customer with chosen id");
        System.out.println("2 - show Customer with chosen name");
        System.out.println("0 - bo back");

        switch (scanner.nextInt()) {
            case 1: {
                printCustomerWithChosenId();
                break;
            }
            case 2: {
                printCustomerWithChosenName();
                break;
            }
            case 0: {
                state = CUSTOMER;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = CUSTOMER;
                break;
            }
        }

        return state;
    }

    private State printCustomerWithChosenId() {
        System.out.println("Enter id:");
        System.out.println(customerRepository
            .findOneById(scanner.nextLong())
            .orElseThrow(() -> new NullPointerException("NO CUSTOMER WITH CHOSEN ID"))
        );
        state = CUSTOMER;
        return state;
    }

    private State printCustomerWithChosenName() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        long id = -1;
        for (Customer c : customerRepository.findAll()) {
            if (name.equals(c.getName())) {
                id = c.getId();
                break;
            }
        }
        System.out.println(customerRepository
            .findOneById(id)
            .orElseThrow(() -> new NullPointerException("NO CUSTOMER WITH CHOSEN NAME"))
        );
        state = CUSTOMER;
        return state;
    }
}
