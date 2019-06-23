package com.app.service.product;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.app.model.Product;
import com.app.repository.order.OrderRepository;
import com.app.service.tools.ServiceTools;

import static com.app.service.provider.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ServiceTools serviceTools;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void should_successfully_find_most_popular_product() {
        // given
        when(orderRepository.findAll()).thenReturn(createOrders());
        when(serviceTools.findProductByOrder(any())).thenReturn(createProduct());

        // when
        Product product = productService.findMostPopularProduct();

        // then
        assertEquals(1, product.getId());
        assertEquals("Jack Daniels", product.getName());
        assertEquals(BigDecimal.valueOf(10), product.getPrice());
        assertEquals(1, product.getCategoryId());
        assertEquals(1, product.getCountryId());
        assertEquals(1, product.getProducerId());
    }
}