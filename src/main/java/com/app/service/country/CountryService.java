package com.app.service.country;

import com.app.model.Country;

import java.util.List;

public interface CountryService {

    List<Country> sortedCountriesByCustomerWhoSpentMost();

    Country mostPopularCountry();
}
