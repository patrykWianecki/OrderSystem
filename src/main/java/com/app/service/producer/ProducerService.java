package com.app.service.producer;

import com.app.model.Producer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProducerService {

    List<Producer> findProducersSortedByTotalAmountSpentOnTheirProducts();

    Map<Producer, BigDecimal> findProducersWithAveragePrice();

    Producer findMostPopularProducer();
}
