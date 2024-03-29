package com.app.repository.category;

import com.app.connection.DbStatus;
import com.app.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    DbStatus add(Category category);

    DbStatus update(Category category);

    DbStatus delete(Long id);

    Optional<Category> findOneById(Long id);

    List<Category> findAll();
}