package com.app.repository.producer;

import com.app.connection.DbStatus;
import com.app.model.Producer;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository {
    DbStatus add(Producer producer);

    DbStatus update(Producer producer);

    DbStatus delete(Long id);

    Optional<Producer> findOneById(Long id);

    List<Producer> findAll();
}
