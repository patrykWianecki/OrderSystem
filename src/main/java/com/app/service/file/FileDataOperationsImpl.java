package com.app.service.file;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
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
    public void addDataToDb() {
        addCategoryFromFileToDb();
        addCountryFromFileToDb();
        addCustomerFromFileToDb();
        addProductFromFileToDb();
        addProducerFromFileToDb();
        addOrdersFromFileToDb();
    }

    @Override
    public void addCategoryFromFileToDb() {
        try {
            FileReader file = new FileReader("category.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.next());
                categoryRepository.add(Category.builder().name(sb.toString()).build());
            }

            file.close();
            scanner.close();
        } catch (IOException e) {
            throw new MyException(ExceptionCode.FILE_OPERATIONS, "Unable to add category from file");
        }
    }

    @Override
    public void addCountryFromFileToDb() {
        try {
            FileReader file = new FileReader("country.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.next());
                countryRepository.add(Country.builder().name(sb.toString()).build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            throw new MyException(ExceptionCode.FILE_OPERATIONS, "Unable to add country from file");
        }
    }

    @Override
    public void addCustomerFromFileToDb() {
        try {
            FileReader file = new FileReader("customer.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                    .split(";");
                customerRepository
                    .add(Customer.builder().name(s[0]).surname(s[1]).age(Integer.valueOf(s[2])).countryId(Long.valueOf(s[3])).build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            throw new MyException(ExceptionCode.FILE_OPERATIONS, "Unable to add customer from file");
        }
    }

    @Override
    public void addProductFromFileToDb() {
        try {
            FileReader file = new FileReader("product.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                    .split(";");
                productRepository.add(
                    Product.builder().name(s[0]).price(BigDecimal.valueOf(Long.parseLong(s[1]))).producerId(Long.valueOf(s[2]))
                        .countryId(Long.valueOf(s[3])).categoryId(Long.valueOf(s[4])).build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            throw new MyException(ExceptionCode.FILE_OPERATIONS, "Unable to add product from file");
        }
    }

    @Override
    public void addProducerFromFileToDb() {
        try {
            FileReader file = new FileReader("producer.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                    .split(";");
                producerRepository.add(Producer.builder().name(s[0]).countryId(Long.valueOf(s[1])).build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            throw new MyException(ExceptionCode.FILE_OPERATIONS, "Unable to add producer from file");
        }
    }

    @Override
    public void addOrdersFromFileToDb() {
        try {
            FileReader file = new FileReader("orders.txt");
            Scanner scanner = new Scanner(file);
            StringBuilder sb;

            while (scanner.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(scanner.nextLine());
                String[] s = sb.toString()
                    .split(";");
                ordersRepository.add(Order.builder().quantity(Integer.valueOf(s[0])).discount(BigDecimal.valueOf(Long.parseLong(s[1])))
                    .date(LocalDate.parse(s[2])).productId(Long.valueOf(s[3])).customerId(Long.valueOf(s[4])).build());
            }

            scanner.close();
            file.close();
        } catch (IOException e) {
            throw new MyException(ExceptionCode.FILE_OPERATIONS, "Unable to add orders from file");
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
        categoryRepository
            .findAll()
            .forEach(category -> categoryRepository.delete(category.getId()));
    }

    @Override
    public void deleteCountriesFromDB() {
        countryRepository
            .findAll()
            .forEach(country -> countryRepository.delete(country.getId()));
    }

    @Override
    public void deleteCustomersFromDB() {
        customerRepository
            .findAll()
            .forEach(customer -> customerRepository.delete(customer.getId()));
    }

    @Override
    public void deleteProductsFromDB() {
        productRepository
            .findAll()
            .forEach(product -> productRepository.delete(product.getId()));
    }

    @Override
    public void deleteProducersFromDB() {
        producerRepository
            .findAll()
            .forEach(producer -> producerRepository.delete(producer.getId()));
    }

    @Override
    public void deleteOrdersFromDB() {
        ordersRepository
            .findAll()
            .forEach(order -> ordersRepository.delete(order.getId()));
    }
}
