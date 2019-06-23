package com.app.service.tools;

import java.util.Comparator;

import com.app.model.Country;
import com.app.model.Customer;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.repository.country.CountryRepository;
import com.app.repository.country.CountryRepositoryImpl;
import com.app.repository.customer.CustomerRepository;
import com.app.repository.customer.CustomerRepositoryImpl;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.producer.ProducerRepositoryImpl;
import com.app.repository.product.ProductRepository;
import com.app.repository.product.ProductRepositoryImpl;

public class MenuTools {

    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();

    public static void showAvailableOperations(String name) {
        System.out.println("Choose operation:");
        System.out.println("1 - Add new " + name);
        System.out.println("2 - Delete " + name);
        System.out.println("3 - Update " + name);
        System.out.println("4 - Show all elements from " + name);
        System.out.println("5 - Show chosen " + name);
        System.out.println("0 - Go back");
    }

    public void showAvailableCountries() {
        System.out.println("Available countries:");
        countryRepository.findAll().forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
    }

    public void showCountriesSortedById() {
        countryRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Country::getId))
            .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
    }

    public void showCustomersSortedById() {
        customerRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Customer::getId))
            .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
    }

    public void showProducersSortedById() {
        producerRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Producer::getId))
            .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
    }

    public void showProductsSortedById() {
        productRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Product::getId))
            .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
    }
}
