package com.app.service.db;

import com.app.connection.DbStatus;
import com.app.model.*;

import java.math.BigDecimal;
import java.util.List;

public interface DbService {

    DbStatus addCustomer(String name, String surname, Integer countryId);

    DbStatus addProduct(String name, BigDecimal price);

    List<Customer> getCustomerByNameAndSurname(String customerName, String customerSurname);

    List<Country> getCountryByName(String countryName);

    List<Product> getProductByName(String productName);

    List<Category> getCategoryByName(String categoryName);

    List<Producer> getProducerByName(String producerName);

    DbStatus addCustomerWithCountry(String customerName, String customerSurname, String countryName);

    DbStatus addProductWithCategoryAndProducer(String productName, String categoryName, String producerName);

    DbStatus addProducerWithCountry(String producerName, String countryName);
}
