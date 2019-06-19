package com.app.service.menu;

import com.app.model.Product;
import com.app.model.State;
import com.app.repository.category.CategoryRepository;
import com.app.repository.category.CategoryRepositoryImpl;
import com.app.repository.country.CountryRepository;
import com.app.repository.country.CountryRepositoryImpl;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.producer.ProducerRepositoryImpl;
import com.app.repository.product.ProductRepository;
import com.app.repository.product.ProductRepositoryImpl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Scanner;

import static com.app.model.State.*;
import static com.app.model.State.INIT;
import static com.app.model.State.PRODUCT;
import static com.app.service.tools.MenuTools.*;
import static com.app.service.tools.MenuTools.showAvailableOperations;

class ProductMenu {

    private static final String TABLE_NAME = "product";

    private ProductRepository productRepository = new ProductRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);
    private State state;

    State printAvailableOperationsOnProducts() {
        showAvailableOperations(TABLE_NAME);

        switch (scanner.nextInt()) {
            case 1: {
                state = printAddProduct();
                break;
            }
            case 2: {
                state = printDeleteProduct();
                break;
            }
            case 3: {
                state = printUpdateProduct();
                break;
            }
            case 4: {
                state = printShowAllProducts();
                break;
            }
            case 5: {
                state = printChosenProduct();
                break;
            }
            case 0: {
                state = INIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = PRODUCT;
                scanner.nextLine();
                break;
            }
        }
        return state;
    }

    private State printAddProduct() {
        System.out.println("Enter Product name:");
        String name = scanner.nextLine();

        System.out.println("Enter Product price:");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.println("Available producers:");
        producerRepository.findAll()
                .forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
        System.out.println("Choose producer id:");
        int producerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Available countries:");
        countryRepository.findAll()
                .forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
        System.out.println("Choose country id:");
        int countryId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Available categories:");
        categoryRepository.findAll()
                .forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
        System.out.println("Choose category id:");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        productRepository.add(Product
                .builder()
                .name(name)
                .price(price)
                .producerId(producerId)
                .countryId(countryId)
                .categoryId(categoryId)
                .build()
        );
        state = PRODUCT;
        return state;
    }

    private State printDeleteProduct() {
        System.out.println("Choose Product id from list to delete:");
        productRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getId))
                .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
        System.out.println("0 - Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            state = PRODUCT;
            return state;
        } else {
            productRepository.delete(choice);
            state = PRODUCT;
            return state;
        }
    }

    private State printUpdateProduct() {
        productRepository
                .findAll()
                .forEach(System.out::println);

        System.out.println("Choose id:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new Product name:");
        String name = scanner.nextLine();

        System.out.println("Enter new Product price:");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.println("Enter new Product producer id:");
        int producerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new Product country id:");
        int countryId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new Product category id:");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        productRepository.update(Product
                .builder()
                .id(choice)
                .name(name)
                .price(price)
                .producerId(producerId)
                .countryId(countryId)
                .categoryId(categoryId)
                .build()
        );
        state = PRODUCT;
        return state;
    }

    private State printShowAllProducts() {
        System.out.println("1 - show all Products sorted by name");
        System.out.println("2 - show all Products sorted by id");
        System.out.println("0 - go back");

        switch (scanner.nextInt()) {
            case 1: {
                printProductsSortedByName();
                break;
            }
            case 2: {
                printProductsSortedById();
                break;
            }
            case 0: {
                state = PRODUCT;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = PRODUCT;
                break;
            }
        }

        return state;
    }

    private State printProductsSortedById() {
        productRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getId))
                .forEach(System.out::println);
        System.out.println("0 - Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            state = PRODUCT;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = PRODUCT;
        }
        return state;
    }

    private State printProductsSortedByName() {
        productRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .forEach(System.out::println);
        System.out.println("0 - Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            state = PRODUCT;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = PRODUCT;
        }
        return state;
    }

    private State printChosenProduct() {
        System.out.println("1 - show Product with chosen id");
        System.out.println("2 - show Product with chosen name");
        System.out.println("0 - bo back");

        switch (scanner.nextInt()) {
            case 1: {
                printProductWithChosenId();
                break;
            }
            case 2: {
                printProductWithChosenName();
                break;
            }
            case 0: {
                state = PRODUCT;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = PRODUCT;
                break;
            }
        }

        return state;
    }

    private State printProductWithChosenId() {
        System.out.println("Enter id:");
        System.out.println(productRepository
                .findOneById(scanner.nextInt())
                .orElseThrow(() -> new NullPointerException("NO PRODUCT WITH CHOSEN ID"))
        );
        state = PRODUCT;
        return state;
    }

    private State printProductWithChosenName() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        int id = -1;
        for (Product c : productRepository.findAll()) {
            if (name.equals(c.getName())) {
                id = c.getId();
                break;
            }
        }
        System.out.println(productRepository
                .findOneById(id)
                .orElseThrow(() -> new NullPointerException("NO PRODUCT WITH CHOSEN ID"))
        );
        state = PRODUCT;
        return state;
    }
}
