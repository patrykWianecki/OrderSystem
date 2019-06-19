package com.app.service.category;

import com.app.exceptions.MyException;
import com.app.model.Category;
import com.app.model.Order;
import com.app.repository.category.CategoryRepository;
import com.app.repository.category.CategoryRepositoryImpl;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.repository.product.ProductRepository;
import com.app.repository.product.ProductRepositoryImpl;
import com.app.service.tools.ServiceTools;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.exceptions.ExceptionCode.SERVICE;

public class CategoryServiceImpl implements CategoryService {

    private OrderRepository ordersRepository = new OrderRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();


    // poprawić to gówno
    @Override
    public Map<Category, List<Order>> sortedCategoriesWithOrders() {
        return ordersRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(o -> categoryRepository
                        .findOneById(productRepository
                                .findOneById(o.getProductId())
                                .orElseThrow(NullPointerException::new)
                                .getCategoryId())
                        .orElseThrow(NullPointerException::new))
                );
    }

    @Override
    public Category findMostPopularCategory() {
        return ordersRepository
                .findAll()
                .stream()
                .map(ServiceTools::findProductById)
                .map(ServiceTools::findCategoryById)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(SERVICE, "Missing most popular category"));
    }
}
