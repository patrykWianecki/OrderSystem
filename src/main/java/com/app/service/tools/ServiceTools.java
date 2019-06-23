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

    private ProductRepository productRepository = new ProductRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    public Category findCategoryByProduct(Product product) {
        return categoryRepository
            .findOneById(product.getCategoryId())
            .orElseThrow(() -> new MyException(SERVICE, "Missing category with id " + product.getCategoryId()));
    }

    public Country findCountryByProduct(Product product) {
        return countryRepository
            .findOneById(product.getCountryId())
            .orElseThrow(() -> new MyException(SERVICE, "Missing producer with id " + product.getProducerId()));
    }

    public Customer findCustomerByOrder(Order order) {
        return customerRepository
            .findOneById(order.getCustomerId())
            .orElseThrow(() -> new MyException(SERVICE, "Missing customer with id " + order.getCustomerId()));
    }

    public Producer findProducerByProduct(Product product) {
        return producerRepository
            .findOneById(product.getProducerId())
            .orElseThrow(() -> new MyException(SERVICE, "Missing producer with id " + product.getProducerId()));
    }

    public Product findProductByOrder(Order order) {
        return productRepository
            .findOneById(order.getProductId())
            .orElseThrow(() -> new MyException(SERVICE, "Missing product with id " + order.getProductId()));
    }
}
