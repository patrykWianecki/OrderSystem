package com.app.repository.customer;

import com.app.connection.DbStatus;
import com.app.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    DbStatus add(Customer customer);

    DbStatus update(Customer customer);

    DbStatus delete(Long id);

    Optional<Customer> findOneById(Long id);

    List<Customer> findAll();
}
