package com.app.service.customer;

import com.app.exceptions.MyException;
import com.app.model.Customer;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.service.tools.ServiceTools;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.exceptions.ExceptionCode.SERVICE;

public class CustomerServiceImpl implements CustomerService {

    private static OrderRepository ordersRepository = new OrderRepositoryImpl();

    @Override
    public Customer mostPopularCustomer() {
        return ordersRepository
                .findAll()
                .stream()
                .map(ServiceTools::findCustomerById)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(SERVICE, "Missing most popular customer"));
    }
}
