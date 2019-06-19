package com.app.repository.country;

import com.app.connection.DbStatus;
import com.app.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    DbStatus add(Country country);

    DbStatus update(Country country);

    DbStatus delete(Integer id);

    Optional<Country> findOneById(Integer id);

    List<Country> findAll();
}
