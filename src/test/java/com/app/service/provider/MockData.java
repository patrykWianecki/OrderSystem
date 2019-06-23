package com.app.service.provider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.app.model.Category;
import com.app.model.Country;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Producer;
import com.app.model.Product;

public class MockData {

    public static Category createCategory() {
        return Category.builder().id(1L).name("Alcohol").build();
    }

    public static Country createCountry() {
        return Country.builder().id(1L).name("Poland").build();
    }

    public static Customer createCustomer() {
        return Customer.builder().id(1L).name("Harry").surname("Potter").age(18).countryId(1L).build();
    }

    public static List<Order> createOrders() {
        return Arrays.asList(
            Order.builder().id(1L).date(LocalDate.now()).discount(BigDecimal.valueOf(10)).quantity(10).productId(1L).customerId(1L).build()
        );
    }

    public static Producer createProducer() {
        return Producer.builder().id(1L).name("Adidas").countryId(1L).build();
    }

    public static Product createProduct() {
        return Product.builder().id(1L).name("Jack Daniels").price(BigDecimal.valueOf(10)).categoryId(1L).countryId(1L).producerId(1L)
            .build();
    }
}
