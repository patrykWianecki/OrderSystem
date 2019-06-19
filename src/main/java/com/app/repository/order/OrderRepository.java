package com.app.repository.order;

import com.app.connection.DbStatus;
import com.app.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    DbStatus add(Order orders);

    DbStatus update(Order orders);

    DbStatus delete(Integer id);

    Optional<Order> findOneById(Integer id);

    List<Order> findAll();
}
