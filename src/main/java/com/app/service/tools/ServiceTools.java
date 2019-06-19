package com.app.service.tools;

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

import static com.app.exceptions.ExceptionCode.SERVICE;

public class ServiceTools {

    private static ProductRepository productRepository = new ProductRepositoryImpl();
    private static ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private static CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private static CountryRepository countryRepository = new CountryRepositoryImpl();
    private static CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    public static Product findProductById(Order order) {
        return productRepository
                .findOneById(order.getProductId())
                .orElseThrow(() -> new MyException(SERVICE, "Missing product with id " + order.getProductId()));
    }

    public static Producer findProducerById(Product product) {
        return producerRepository
                .findOneById(product.getProducerId())
                .orElseThrow(() -> new MyException(SERVICE, "Missing producer with id " + product.getProducerId()));
    }

    public static Customer findCustomerById(Order order) {
        return customerRepository
                .findOneById(order.getCustomerId())
                .orElseThrow(() -> new MyException(SERVICE, "Missing customer with id " + order.getCustomerId()));
    }

    public static Country findCountryById(Product product) {
        return countryRepository
                .findOneById(product.getCountryId())
                .orElseThrow(() -> new MyException(SERVICE, "Missing producer with id " + product.getProducerId()));
    }

    public static Category findCategoryById(Product product) {
        return categoryRepository
                .findOneById(product.getCategoryId())
                .orElseThrow(() -> new MyException(SERVICE, "Missing category with id " + product.getCategoryId()));
    }
}
