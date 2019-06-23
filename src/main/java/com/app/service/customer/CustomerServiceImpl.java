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

    private OrderRepository ordersRepository = new OrderRepositoryImpl();
    private ServiceTools serviceTools = new ServiceTools();

    @Override
    public Customer findMostPopularCustomer() {
        return ordersRepository.findAll()
            .stream()
            .map(serviceTools::findCustomerByOrder)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new MyException(SERVICE, "Missing most popular customer"));
    }
}
