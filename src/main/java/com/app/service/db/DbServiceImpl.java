package com.app.service.db;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.*;
import com.app.repository.category.CategoryRepository;
import com.app.repository.category.CategoryRepositoryImpl;
import com.app.repository.country.CountryRepository;
import com.app.repository.country.CountryRepositoryImpl;
import com.app.repository.customer.CustomerRepository;
import com.app.repository.customer.CustomerRepositoryImpl;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.producer.ProducerRepositoryImpl;
import com.app.repository.product.ProductRepository;
import com.app.repository.product.ProductRepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DbServiceImpl implements DbService {

    private Connection connection = DbConnection.getInstance().getConnection();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public DbStatus addCustomer(String name, String surname, Integer countryId) {
        return null;
    }

    @Override
    public DbStatus addProduct(String name, BigDecimal price) {
        return null;
    }

    @Override
    public List<Customer> getCustomerByNameAndSurname(String customerName, String customerSurname) {
        return customerRepository
            .findAll()
            .stream()
            .filter(customer -> customer.getName().equals(customerName) && customer.getSurname().equals(customerSurname))
            .collect(Collectors.toList());
    }

    @Override
    public List<Country> getCountryByName(String countryName) {
        return countryRepository
            .findAll()
            .stream()
            .filter(country -> country.getName().equals(countryName))
            .collect(Collectors.toList());
    }

    @Override
    public List<Category> getCategoryByName(String categoryName) {
        return categoryRepository
            .findAll()
            .stream()
            .filter(category -> category.getName().equals(categoryName))
            .collect(Collectors.toList());
    }

    @Override
    public List<Producer> getProducerByName(String producerName) {
        return producerRepository
            .findAll()
            .stream()
            .filter(producer -> producer.getName().equals(producerName))
            .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository
            .findAll()
            .stream()
            .filter(product -> product.getName().equals(productName))
            .collect(Collectors.toList());
    }

    @Override
    public DbStatus addCustomerWithCountry(String customerName, String customerSurname, String countryName) {
        try {
            connection.setAutoCommit(false);

            Customer customer = null;
            Country country = null;

            // CUSTOMERS
            List<Customer> customers = getCustomerByNameAndSurname(customerName, customerSurname);
            if (customers == null || customers.isEmpty()) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN Customer " + customerName + " " + customerSurname);
            } else if (customers.size() == 1) {
                customer = customers.get(0);
            } else {
                for (int i = 0; i < customers.size(); i++) {
                    System.out.println((i + 1) + ". " + customers.get(i).getName() + " " + customers.get(i).getSurname());
                }
                int choice = 0;
                Scanner sc = new Scanner(System.in);
                do {
                    System.out.println("Select customer from list - choose number:");
                    choice = sc.nextInt();
                    sc.nextLine();
                } while (choice < 1 || choice > customers.size());
                customer = customers.get(choice);
            }

            // COUNTRIES
            List<Country> countries = getCountryByName(countryName);
            if (countries == null || countries.isEmpty()) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN Country " + country.getName());
            } else {
                country = getCountry(countries);
            }

            customer.setCountryId(country.getId());
            customerRepository.update(customer);
            System.out.println("INSERTED CLIENT WITH COUNTRY");

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e1) {
                throw new MyException(ExceptionCode.SERVICE,
                    "FAILED TO ROLLBACK TRANSACTION WHILE INSERTING CLIENT WITH COUNTRY [ ERROR " + e.getMessage() + " ]");
            }
            System.err.println("FAILED TO INSERT CLIENT WITH COUNTRY [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                System.err.println("FAILED TO INSERT CLIENT WITH COUNTRY [ ERROR " + e.getMessage() + " ]");
            }
        }

        System.out.println("SUCCESSFULLY INSERTED CLIENT WITH COUNTRY");
        return DbStatus.OK;
    }

    @Override
    public DbStatus addProductWithCategoryAndProducer(String productName, String categoryName, String producerName) {
        try {
            connection.setAutoCommit(false);

            Product product;
            Category category;
            Producer producer;

            // CATEGORIES
            List<Category> categories = getCategoryByName(categoryName);
            if (categories == null || categories.size() == 0) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN Category " + categoryName);
            } else if (categories.size() == 1) {
                category = categories.get(0);
            } else {
                for (int i = 0; i < categories.size(); i++) {
                    System.out.println((i + 1) + ". " + categories.get(i).getName());
                }
                int choice = 0;
                Scanner scanner = new Scanner(System.in);
                do {
                    System.out.println("Select category from list - choose number:");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } while (choice < 1 || choice > categories.size());
                category = categories.get(choice);
            }

            // PRODUCTS
            List<Product> products = getProductByName(productName);
            if (products == null || products.size() == 0) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN Product " + productName);
            } else if (products.size() == 1) {
                product = products.get(0);
            } else {
                for (int i = 0; i < products.size(); i++) {
                    System.out.println((i + 1) + ". " + products.get(i).getName());
                }
                int choice = 0;
                Scanner scanner = new Scanner(System.in);
                do {
                    System.out.println("Select Product from list - choose number:");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } while (choice < 1 || choice > products.size());
                product = products.get(choice);
            }

            // PRODUCERS
            List<Producer> producers = getProducerByName(producerName);
            if (producers == null || producers.size() == 0) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN PRODUCER " + producerName);
            } else {
                producer = getProducer(producers);
            }

            product.setCategoryId(category.getId());
            product.setProducerId(producer.getId());
            productRepository.update(product);
            System.out.println("INSERTED PRODUCT WITH CATEGORY AND PRODUCER");

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e1) {
                throw new MyException(ExceptionCode.SERVICE,
                    "FAILED TO ROLLBACK TRANSACTION WHILE INSERTING PRODUCT WITH CATEGORY AND PRODUCER [ ERROR " + e.getMessage() + " ]");
            }
            System.err.println("FAILED TO INSERT PRODUCT WITH CATEGORY AND PRODCUER [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                System.err.println("FAILED TO INSERT PRODUCT WITH CATEGORY AND PRODUCER [ ERROR " + e.getMessage() + " ]");
            }
        }

        System.out.println("SUCCESSFULLY INSERTED PRODUCT WITH CATEGORY AND PRODUCER");
        return DbStatus.OK;
    }

    @Override
    public DbStatus addProducerWithCountry(String producerName, String countryName) {
        try {
            connection.setAutoCommit(false);

            Producer producer;
            Country country;

            // PRODUCERS
            List<Producer> producers = getProducerByName(producerName);
            if (producers == null || producers.size() == 0) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN Producer " + producerName);
            } else {
                producer = getProducer(producers);
            }

            //COUNTRIES
            List<Country> countries = getCountryByName(countryName);
            if (countries == null || countries.size() == 0) {
                throw new NullPointerException("TABLE DOES NOT CONTAIN Country " + countryName);
            } else {
                country = getCountry(countries);
            }

            producer.setCountryId(country.getId());
            producerRepository.update(producer);
            System.out.println("INSERTED PRODUCER WITH COUNTRY");

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e1) {
                throw new MyException(ExceptionCode.SERVICE,
                    "FAILED TO ROLLBACK TRANSACTION WHILE INSERTING PRODUCER WITH COUNTRY [ ERROR " + e.getMessage() + " ]");
            }
            System.err.println("FAILED TO INSERT PRODUCER WITH COUNTRY [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                System.err.println("FAILED TO INSERT PRODUCER WITH COUNTRY [ ERROR " + e.getMessage() + " ]");
            }
        }

        System.out.println("SUCCESSFULLY INSERTED PRODUCER WITH COUNTRY");
        return DbStatus.OK;
    }

    private Producer getProducer(List<Producer> producers) {
        Producer producer;
        if (producers.size() == 1) {
            producer = producers.get(0);
        } else {
            for (int i = 0; i < producers.size(); i++) {
                System.out.println((i + 1) + ". " + producers.get(i).getName());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select Producer from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 1 || choice > producers.size());
            producer = producers.get(choice);
        }
        return producer;
    }

    private Country getCountry(List<Country> countries) {
        Country country;
        if (countries.size() == 1) {
            country = countries.get(0);
        } else {
            for (int i = 0; i < countries.size(); i++) {
                System.out.println((i + 1) + ". " + countries.get(i).getName());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select Country from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 1 || choice > countries.size());
            country = countries.get(choice);
        }
        return country;
    }
}
