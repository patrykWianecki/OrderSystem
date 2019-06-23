package com.app.service.menu;

import com.app.model.Order;
import com.app.model.State;
import com.app.repository.customer.CustomerRepository;
import com.app.repository.customer.CustomerRepositoryImpl;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.repository.product.ProductRepository;
import com.app.repository.product.ProductRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;

import static com.app.model.State.INIT;
import static com.app.model.State.ORDER;
import static com.app.service.tools.MenuTools.showAvailableOperations;

class OrderMenu {

    private static final String TABLE_NAME = "order";

    private static Scanner scanner = new Scanner(System.in);
    private static State state;

    private OrderRepository orderRepository = new OrderRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();

    State printAvailableOperationsOnOrders() {
        showAvailableOperations(TABLE_NAME);

        switch (scanner.nextInt()) {
            case 1: {
                state = printAddOrder();
                break;
            }
            case 2: {
                state = printDeleteOrder();
                break;
            }
            case 3: {
                state = printUpdateOrder();
                break;
            }
            case 4: {
                state = printShowAllOrder();
                break;
            }
            case 5: {
                state = printOrderWithChosenId();
                break;
            }
            case 0: {
                state = INIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = ORDER;
                scanner.nextLine();
                break;
            }
        }
        return state;
    }

    private State printAddOrder() {
        System.out.println("Available products:");
        productRepository.findAll()
            .forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
        System.out.println("Choose product id:");
        long productId = scanner.nextLong();
        scanner.nextLine();

        customerRepository.findAll()
            .forEach(s -> System.out.println(s.getId() + ". " + s.getName() + " " + s.getSurname()));
        System.out.println("Choose customer id:");
        long customerId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter Order quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Order discount");
        BigDecimal discount = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.println("Enter Order date:");
        System.out.println("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        orderRepository.add(Order
            .builder()
            .quantity(quantity)
            .discount(discount)
            .date(date)
            .productId(productId)
            .customerId(customerId)
            .build()
        );
        state = ORDER;
        return state;
    }

    private State printDeleteOrder() {
        System.out.println("Choose Order id from list to delete:");
        orderRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Order::getId))
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = ORDER;
            return state;
        } else {
            orderRepository.delete(choice);
            state = ORDER;
            return state;
        }
    }

    private State printUpdateOrder() {
        orderRepository
            .findAll()
            .forEach(System.out::println);

        System.out.println("Choose id:");
        long choice = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter Order quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Order discount");
        BigDecimal discount = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.println("Enter Order date:");
        System.out.println("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter Order product id:");
        long productId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter Order customer id:");
        long customerId = scanner.nextLong();
        scanner.nextLine();

        orderRepository.update(Order
            .builder()
            .id(choice)
            .quantity(quantity)
            .discount(discount)
            .date(date)
            .productId(productId)
            .customerId(customerId)
            .build()
        );
        state = ORDER;
        return state;
    }

    private State printShowAllOrder() {
        System.out.println("1 - show all Order sorted by id");
        System.out.println("2 - show all Order sorted by quantity");
        System.out.println("3 - show all Order sorted by discount");
        System.out.println("4 - show all Order sorted by date");
        System.out.println("0 - go back");

        switch (scanner.nextInt()) {
            case 1: {
                printOrderortedById();
                break;
            }
            case 2: {
                printOrderortedByQuantity();
                break;
            }
            case 3: {
                printOrderortedByDiscount();
                break;
            }
            case 4: {
                printOrderortedByDate();
                break;
            }
            case 0: {
                state = ORDER;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = ORDER;
                break;
            }
        }

        return state;
    }

    private State printOrderortedById() {
        orderRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Order::getId))
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = ORDER;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = ORDER;
        }
        return state;
    }

    private State printOrderortedByQuantity() {
        return getState(Comparator.comparing(Order::getQuantity));
    }

    private State printOrderortedByDiscount() {
        return getState(Comparator.comparing(Order::getDiscount));
    }

    private State printOrderortedByDate() {
        return getState(Comparator.comparing(Order::getDate));
    }

    private State getState(Comparator<Order> comparing) {
        orderRepository
            .findAll()
            .stream()
            .sorted(comparing)
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = ORDER;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = ORDER;
        }
        return state;
    }

    private State printOrderWithChosenId() {
        System.out.println("Enter id:");
        System.out.println(orderRepository
            .findOneById(scanner.nextLong())
            .orElseThrow(() -> new NullPointerException("NO ORDER WITH CHOSEN ID"))
        );
        state = ORDER;
        return state;
    }
}
