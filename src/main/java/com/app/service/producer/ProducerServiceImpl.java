package com.app.service.producer;

import com.app.exceptions.MyException;
import com.app.model.Order;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.repository.order.OrderRepository;
import com.app.repository.order.OrderRepositoryImpl;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.producer.ProducerRepositoryImpl;
import com.app.service.tools.ServiceTools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.exceptions.ExceptionCode.SERVICE;
import static com.app.service.tools.ServiceTools.findProductById;

public class ProducerServiceImpl implements ProducerService {
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private OrderRepository ordersRepository = new OrderRepositoryImpl();

    @Override
    public List<Producer> sortProducersByTotalAmountSpentOnProducts() {
        return ordersRepository
                .findAll()
                .stream()
                .map(ServiceTools::findProductById)
                .map(ServiceTools::findProducerById)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // poprawić to gówno
    @Override
    public Map<Producer, BigDecimal> producersWithAveragePrice() {
        return ordersRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(order -> producerRepository
                        .findOneById(findProductById(order).getProducerId())
                        .orElseThrow(NullPointerException::new)
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    BigDecimal sum = BigDecimal.ZERO;
                    for (Order o : e.getValue()) {
                        Product product = findProductById(o);
                        sum = sum.add(product
                                .getPrice()
                                .multiply(BigDecimal.valueOf(o.getQuantity()))
                                .multiply(BigDecimal.ONE.subtract(o.getDiscount()
                                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR))));
                    }
                    return sum.divide(BigDecimal.valueOf(e.getValue()
                            .size()), 2, RoundingMode.FLOOR);
                }));
    }

    @Override
    public Producer getMostPopularProducer() {
        return ordersRepository
                .findAll()
                .stream()
                .map(ServiceTools::findProductById)
                .map(ServiceTools::findProducerById)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException(SERVICE, "Missing most popular producer"));
    }

}
