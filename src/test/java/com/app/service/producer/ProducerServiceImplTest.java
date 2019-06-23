package com.app.service.producer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.app.model.Producer;
import com.app.repository.order.OrderRepository;
import com.app.service.tools.ServiceTools;

import static com.app.service.provider.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProducerServiceImplTest {

    @Mock
    private OrderRepository ordersRepository;
    @Mock
    private ServiceTools serviceTools;

    @InjectMocks
    private ProducerServiceImpl producerService;

    @BeforeEach
    void setUp() {
        when(ordersRepository.findAll()).thenReturn(createOrders());
        when(serviceTools.findProductByOrder(any())).thenReturn(createProduct());
        when(serviceTools.findProducerByProduct(any())).thenReturn(createProducer());
    }

    @Test
    void should_successfully_find_most_popular_producer() {
        // given

        // when
        Producer actualProducer = producerService.findMostPopularProducer();

        // then
        assertEquals(1, actualProducer.getId());
        assertEquals(1, actualProducer.getCountryId());
        assertEquals("Adidas", actualProducer.getName());
    }

    @Test
    void should_successfully_find_producers_sorted_by_total_amount_spent_on_products() {
        // given

        // when
        List<Producer> actualProducers = producerService.findProducersSortedByTotalAmountSpentOnTheirProducts();

        // then
        assertEquals(1, actualProducers.size());
        assertEquals(1, actualProducers.get(0).getId());
        assertEquals(1, actualProducers.get(0).getCountryId());
        assertEquals("Adidas", actualProducers.get(0).getName());
    }

    @Test
    void should_successfully_find_producers_with_average_price() {
        // given

        // when
        Map<Producer, BigDecimal> actualProducers = producerService.findProducersWithAveragePrice();

        // then
        Producer producer = actualProducers.keySet().stream().findFirst().orElse(null);
        assertEquals(1, actualProducers.keySet().size());
        assertEquals(1, producer.getId());
        assertEquals(1, producer.getCountryId());
        assertEquals("Adidas", producer.getName());
        assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.CEILING), actualProducers.values().stream().findFirst().orElse(null));
    }
}