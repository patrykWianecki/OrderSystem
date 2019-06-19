package com.app.service.file;

import com.app.model.*;
import com.app.repository.category.CategoryRepository;
import com.app.repository.category.CategoryRepositoryImpl;
import com.app.repository.country.CountryRepository;
import com.app.repository.country.CountryRepositoryImpl;
import com.app.repository.customer.CustomerRepository;
import com.app.repository.customer.CustomerRepositoryImpl;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.producer.ProducerRepositoryImpl;
import com.app.repository.product.ProductRepository;
import com.app.repository.product.ProductRepositoryImpl;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class FileDataOperationsImpl implements FileDataOperations {

    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private OrderRepository ordersRepository = new OrderRepositoryImpl();

    @Override
    public void addDataToDB() {
        addCategoryFromFileToDB();
        addCountryFromFileToDB();
        addCustomerFromFileToDB();
        addProductFromFileToDB();
        addProducerFromFileToDB();
        addOrdersFromFileToDB();
    }

    @Override
    public void addCategoryFromFileToDB() {
        try {
            FileReader file = new FileReader("category.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.next());
                categoryRepository.add(Category
                        .builder()
                        .name(sb.toString())
                        .build()
                );
            }

            file.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCountryFromFileToDB() {
        try {
            FileReader file = new FileReader("country.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.next());
                countryRepository.add(Country.builder()
                        .name(sb.toString())
                        .build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCustomerFromFileToDB() {
        try {
            FileReader file = new FileReader("customer.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                        .split(";");
                customerRepository.add(Customer
                        .builder()
                        .name(s[0])
                        .surname(s[1])
                        .age(Integer.valueOf(s[2]))
                        .countryId(Integer.valueOf(s[3]))
                        .build()
                );
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProductFromFileToDB() {
        try {
            FileReader file = new FileReader("product.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                        .split(";");
                productRepository.add(Product
                        .builder()
                        .name(s[0])
                        .price(BigDecimal.valueOf(Long.parseLong(s[1])))
                        .producerId(Integer.valueOf(s[2]))
                        .countryId(Integer.valueOf(s[3]))
                        .categoryId(Integer.valueOf(s[4]))
                        .build()
                );
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProducerFromFileToDB() {
        try {
            FileReader file = new FileReader("producer.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                        .split(";");
                producerRepository.add(Producer
                        .builder()
                        .name(s[0])
                        .countryId(Integer.valueOf(s[1]))
                        .build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOrdersFromFileToDB() {
        try {
            FileReader file = new FileReader("orders.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                        .split(";");
                ordersRepository.add(Order
                        .builder()
                        .quantity(Integer.valueOf(s[0]))
                        .discount(BigDecimal.valueOf(Long.parseLong(s[1])))
                        .date(LocalDate.parse(s[2]))
                        .productId(Integer.valueOf(s[3]))
                        .customerId(Integer.valueOf(s[4]))
                        .build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDataFromDB() {
        deleteCategoriesFromDB();
        deleteCountriesFromDB();
        deleteCustomersFromDB();
        deleteProductsFromDB();
        deleteProducersFromDB();
        deleteOrdersFromDB();
    }

    @Override
    public void deleteCategoriesFromDB() {
        categoryRepository.findAll()
                .forEach(category -> categoryRepository.delete(category.getId()));
    }

    @Override
    public void deleteCountriesFromDB() {
        countryRepository.findAll()
                .forEach(country -> countryRepository.delete(country.getId()));
    }

    @Override
    public void deleteCustomersFromDB() {
        customerRepository.findAll()
                .forEach(customer -> customerRepository.delete(customer.getId()));
    }

    @Override
    public void deleteProductsFromDB() {
        productRepository.findAll()
                .forEach(product -> productRepository.delete(product.getId()));
    }

    @Override
    public void deleteProducersFromDB() {
        producerRepository.findAll()
                .forEach(producer -> producerRepository.delete(producer.getId()));
    }

    @Override
    public void deleteOrdersFromDB() {
        ordersRepository.findAll()
                .forEach(order -> ordersRepository.delete(order.getId()));
    }
}
