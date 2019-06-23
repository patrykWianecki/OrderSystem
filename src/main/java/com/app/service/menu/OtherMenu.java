package com.app.service.menu;

import com.app.model.State;
import com.app.service.category.CategoryService;
import com.app.service.category.CategoryServiceImpl;
import com.app.service.country.CountryService;
import com.app.service.country.CountryServiceImpl;
import com.app.service.customer.CustomerService;
import com.app.service.customer.CustomerServiceImpl;
import com.app.service.file.FileDataOperations;
import com.app.service.file.FileDataOperationsImpl;
import com.app.service.producer.ProducerService;
import com.app.service.producer.ProducerServiceImpl;
import com.app.service.product.ProductService;
import com.app.service.product.ProductServiceImpl;

import java.util.Scanner;

import static com.app.model.State.INIT;
import static com.app.model.State.OTHER;

class OtherMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static State state;

    private FileDataOperations fileDataOperations = new FileDataOperationsImpl();
    private CategoryService categoryService = new CategoryServiceImpl();
    private CountryService countryService = new CountryServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();
    private ProducerService producerService = new ProducerServiceImpl();
    private ProductService productService = new ProductServiceImpl();

    State printOtherAvailableOperations() {
        System.out.println("Choose operation:");
        System.out.println("1 - show the most popular Category");
        System.out.println("2 - show the most popular Country");
        System.out.println("3 - show the most popular Customer");
        System.out.println("4 - show the most popular Producer");
        System.out.println("5 - show the most popular Product");
        System.out.println("6 - show Categories with all Orders");
        System.out.println("7 - show sorted Countries with customer who spent most");
        System.out.println("8 - show Producers sorted by Orders number");
        System.out.println("9 - show Producers with average price spent on their Products");
        System.out.println("10 - add data to data base");
        System.out.println("11 - delete data from data base");
        System.out.println("0 - Go back");

        switch (scanner.nextInt()) {
            case 1:
                printMostPopularCategory();
                break;
            case 2:
                printMostPopularCountry();
                break;
            case 3:
                printMostPopularCustomer();
                break;
            case 4:
                printMostPopularProducer();
                break;
            case 5:
                printMostPopularProduct();
                break;
            case 6:
                printSortedCategoriesWithOrders();
                break;
            case 7:
                printSortedCountriesOrderedByCustomerWhoSpentMost();
                break;
            case 8:
                printSortedProducersByOrdersNumber();
                break;
            case 9:
                printProducersWithAveragePriceSpentOnTheirProduct();
                break;
            case 10:
                addDataToDb();
                break;
            case 11:
                deleteDataFromDB();
                break;
            case 0:
                state = INIT;
                scanner.nextLine();
                break;
            default:
                System.out.println("Wrong answer!");
                state = OTHER;
                scanner.nextLine();
                break;
        }

        return state;
    }

    private State printMostPopularCategory() {
        System.out.println(categoryService.findMostPopularCategory());
        state = OTHER;
        return state;
    }

    private State printMostPopularCountry() {
        System.out.println(countryService.findMostPopularCountry());
        state = OTHER;
        return state;
    }

    private State printMostPopularCustomer() {
        System.out.println(customerService.findMostPopularCustomer());
        state = OTHER;
        return state;
    }

    private State printMostPopularProducer() {
        System.out.println(producerService.findMostPopularProducer());
        state = OTHER;
        return state;
    }

    private State printMostPopularProduct() {
        System.out.println(productService.findMostPopularProduct());
        state = OTHER;
        return state;
    }

    private State printSortedCategoriesWithOrders() {
        System.out.println(categoryService.findCategoriesWithSortedOrders());
        state = OTHER;
        return state;
    }

    private State printSortedCountriesOrderedByCustomerWhoSpentMost() {
        System.out.println(countryService.sortedCountriesByCustomerWhoSpentMost());
        state = OTHER;
        return state;
    }

    private State printSortedProducersByOrdersNumber() {
        System.out.println(producerService.findProducersSortedByTotalAmountSpentOnTheirProducts());
        state = OTHER;
        return state;
    }

    private State printProducersWithAveragePriceSpentOnTheirProduct() {
        System.out.println(producerService.findProducersWithAveragePrice());
        state = OTHER;
        return state;
    }

    private State addDataToDb() {
        System.out.println("1 - add categories from file");
        System.out.println("2 - add countries from file");
        System.out.println("3 - add customers from file");
        System.out.println("4 - add products from file");
        System.out.println("5 - add producers from file");
        System.out.println("6 - add orders from file");
        System.out.println("7 - add all data");
        System.out.println("0 - go back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addCategoriesToDb();
                break;
            case 2:
                addCountriesToDb();
                break;
            case 3:
                addCustomersToDb();
                break;
            case 4:
                addProductsToDb();
                break;
            case 5:
                addProducersToDb();
                break;
            case 6:
                addOrdersToDb();
                break;
            case 7:
                addAllToDb();
                break;
            case 0:
                state = OTHER;
                return state;
            default:
                System.out.println("Wrong answer");
                break;
        }
        state = OTHER;
        return state;
    }

    private State addCategoriesToDb() {
        fileDataOperations.addCategoryFromFileToDb();
        state = OTHER;
        return state;
    }

    private State addCountriesToDb() {
        fileDataOperations.addCountryFromFileToDb();
        state = OTHER;
        return state;
    }

    private State addCustomersToDb() {
        fileDataOperations.addCustomerFromFileToDb();
        state = OTHER;
        return state;
    }

    private State addProductsToDb() {
        fileDataOperations.addProductFromFileToDb();
        state = OTHER;
        return state;
    }

    private State addProducersToDb() {
        fileDataOperations.addProducerFromFileToDb();
        state = OTHER;
        return state;
    }

    private State addOrdersToDb() {
        fileDataOperations.addOrdersFromFileToDb();
        state = OTHER;
        return state;
    }

    private State addAllToDb() {
        fileDataOperations.addDataToDb();
        state = OTHER;
        return state;
    }

    private State deleteDataFromDB() {
        System.out.println("1 - delete categories from data base");
        System.out.println("2 - add countries from data base");
        System.out.println("3 - add customers from data base");
        System.out.println("4 - add products from data base");
        System.out.println("5 - add producers from data base");
        System.out.println("6 - add orders from data base");
        System.out.println("7 - delete all data");
        System.out.println("0 - go back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                deleteCategoriesFromDB();
                break;
            case 2:
                deleteCountriesFromDB();
                break;
            case 3:
                deleteCustomersFromDB();
                break;
            case 4:
                deleteProductsFromDB();
                break;
            case 5:
                deleteProducersFromDB();
                break;
            case 6:
                deleteOrdersFromDB();
                break;
            case 7:
                deleteAllDataFromDB();
                break;
            case 0:
                state = OTHER;
                return state;
            default:
                System.out.println("Wrong answer");
                break;
        }
        state = OTHER;
        return state;
    }

    private State deleteCategoriesFromDB() {
        fileDataOperations.deleteCategoriesFromDB();
        state = OTHER;
        return state;
    }

    private State deleteCountriesFromDB() {
        fileDataOperations.deleteCountriesFromDB();
        state = OTHER;
        return state;
    }

    private State deleteCustomersFromDB() {
        fileDataOperations.deleteCustomersFromDB();
        state = OTHER;
        return state;
    }

    private State deleteProductsFromDB() {
        fileDataOperations.deleteProductsFromDB();
        state = OTHER;
        return state;
    }

    private State deleteProducersFromDB() {
        fileDataOperations.deleteProducersFromDB();
        state = OTHER;
        return state;
    }

    private State deleteOrdersFromDB() {
        fileDataOperations.deleteOrdersFromDB();
        state = OTHER;
        return state;
    }

    private State deleteAllDataFromDB() {
        fileDataOperations.deleteDataFromDB();
        state = OTHER;
        return state;
    }
}
