package com.app.service.category;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.app.exceptions.MyException;
import com.app.model.Category;
import com.app.model.Order;
import com.app.model.Product;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.service.tools.ServiceTools;

import static com.app.exceptions.ExceptionCode.*;

public class CategoryServiceImpl implements CategoryService {

    private OrderRepository ordersRepository = new OrderRepositoryImpl();
    private ServiceTools serviceTools = new ServiceTools();

    @Override
    public Map<Category, List<Order>> findCategoriesWithSortedOrders() {
        return ordersRepository
            .findAll()
            .stream()
            .collect(Collectors.groupingBy(
                order -> {
                    Product product = serviceTools.findProductByOrder(order);
                    return serviceTools.findCategoryByProduct(product);
                }, Collectors.toList())
            );
    }

    @Override
    public Category findMostPopularCategory() {
        return ordersRepository.findAll()
            .stream()
            .map(serviceTools::findProductByOrder)
            .map(serviceTools::findCategoryByProduct)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new MyException(SERVICE, "Missing most popular category"));
    }
}
