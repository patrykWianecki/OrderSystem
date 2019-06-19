package com.app.service.product;

import com.app.exceptions.MyException;
import com.app.model.Product;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.service.tools.ServiceTools;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.exceptions.ExceptionCode.SERVICE;

public class ProductServiceImpl implements ProductService {

    private static OrderRepository ordersRepository = new OrderRepositoryImpl();

    @Override
    public Product mostPopularProduct() {
        return ordersRepository
                .findAll()
                .stream()
                .map(ServiceTools::findProductById)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(SERVICE, "Missing most popular product"));
    }
}
