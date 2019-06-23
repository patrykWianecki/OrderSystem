package com.app.service.producer;

import com.app.exceptions.MyException;
import com.app.model.Order;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.service.tools.ServiceTools;

import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.exceptions.ExceptionCode.SERVICE;

public class ProducerServiceImpl implements ProducerService {

    private OrderRepository ordersRepository = new OrderRepositoryImpl();
    private ServiceTools serviceTools = new ServiceTools();

    @Override
    public List<Producer> findProducersSortedByTotalAmountSpentOnTheirProducts() {
        return new ArrayList<>(collectOrdersWithPrices()
            .entrySet()
            .stream()
            .collect(Collectors.groupingBy(
                entry -> {
                    Product product = serviceTools.findProductByOrder(entry.getKey());
                    return serviceTools.findProducerByProduct(product);
                },
                Collectors2.summarizingBigDecimal(Map.Entry::getValue))
            ).entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                sum -> sum
                    .getValue()
                    .getSum())
            ).entrySet()
            .stream()
            .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (c1, c2) -> c1,
                LinkedHashMap::new)
            ).keySet()
        );
    }

    private Map<Order, BigDecimal> collectOrdersWithPrices() {
        return ordersRepository
            .findAll()
            .stream()
            .collect(Collectors.toMap(
                order -> order,
                value -> {
                    Product product = serviceTools.findProductByOrder(value);

                    BigDecimal price = product
                        .getPrice()
                        .multiply(BigDecimal.valueOf(value.getQuantity()));
                    BigDecimal discount = price.subtract((price
                        .multiply(value.getDiscount()))
                        .divide(BigDecimal.valueOf(100), RoundingMode.CEILING));

                    return price.subtract(discount);
                }
            ));
    }

    @Override
    public Map<Producer, BigDecimal> findProducersWithAveragePrice() {
        return collectOrdersWithPrices()
            .entrySet()
            .stream()
            .collect(Collectors.groupingBy(entry -> {
                    Product product = serviceTools.findProductByOrder(entry.getKey());
                    return serviceTools.findProducerByProduct(product);
                },
                Collectors2.summarizingBigDecimal(Map.Entry::getValue)))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                sum -> sum.getValue().getAverage().setScale(2, RoundingMode.CEILING))
            );
    }

    @Override
    public Producer findMostPopularProducer() {
        return ordersRepository
            .findAll()
            .stream()
            .map(serviceTools::findProductByOrder)
            .map(serviceTools::findProducerByProduct)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new MyException(SERVICE, "Missing most popular producer"));
    }
}
