package com.app.repository.product;

import com.app.connection.DbStatus;
import com.app.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    DbStatus add(Product product);

    DbStatus update(Product product);

    DbStatus delete(Integer id);

    Optional<Product> findOneById(Integer id);

    List<Product> findAll();
}
