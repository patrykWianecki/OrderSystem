package com.app.service.country;

import com.app.exceptions.MyException;
import com.app.model.Country;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.service.tools.ServiceTools;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.exceptions.ExceptionCode.SERVICE;

public class CountryServiceImpl implements CountryService {

    private OrderRepository ordersRepository = new OrderRepositoryImpl();

    @Override
    public List<Country> sortedCountriesByCustomerWhoSpentMost() {
        return null;
    }

    @Override
    public Country mostPopularCountry() {
        return ordersRepository
                .findAll()
                .stream()
                .map(ServiceTools::findProductById)
                .map(ServiceTools::findCountryById)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(SERVICE, "Missing most popular country"));
    }
}
