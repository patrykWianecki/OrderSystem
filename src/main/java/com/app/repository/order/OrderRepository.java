package com.app.repository.order;

import com.app.connection.DbStatus;
import com.app.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    DbStatus add(Order orders);

    DbStatus update(Order orders);

    DbStatus delete(Long id);

    Optional<Order> findOneById(Long id);

    List<Order> findAll();
}
