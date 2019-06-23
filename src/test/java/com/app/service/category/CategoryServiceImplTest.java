package com.app.service.category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.app.model.Category;
import com.app.model.Order;
import com.app.repository.order.OrderRepository;
import com.app.service.tools.ServiceTools;

import static com.app.service.provider.MockData.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ServiceTools serviceTools;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        when(orderRepository.findAll()).thenReturn(createOrders());
        when(serviceTools.findProductByOrder(any())).thenReturn(createProduct());
        when(serviceTools.findCategoryByProduct(any())).thenReturn(createCategory());
    }

    @Test
    void should_find_categories_sorted_with_orders() {
        // given

        // then
        Map<Category, List<Order>> actual = categoryService.findCategoriesWithSortedOrders();

        // then
        List<Order> actualOrders = actual.values().stream().flatMap(Collection::stream).collect(Collectors.toList());

        assertEquals(1, actual.keySet().size());
        assertEquals(1, actual.values().size());
        assertEquals(Long.valueOf(1), actualOrders.get(0).getId());
        assertEquals(BigDecimal.valueOf(10), actualOrders.get(0).getDiscount());
        assertEquals(LocalDate.now(), actualOrders.get(0).getDate());
        assertEquals(Integer.valueOf(10), actualOrders.get(0).getQuantity());
        assertEquals(Long.valueOf(1), actualOrders.get(0).getProductId());
        assertEquals(Long.valueOf(1), actualOrders.get(0).getCustomerId());
    }

    @Test
    void should_find_the_most_popular_category() {
        // given

        // when
        Category actualCategory = categoryService.findMostPopularCategory();

        // then
        assertEquals(Long.valueOf(1), actualCategory.getId());
        assertEquals("Alcohol", actualCategory.getName());
    }
}