package com.app.service.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.app.model.Customer;
import com.app.repository.order.OrderRepository;
import com.app.service.tools.ServiceTools;

import static com.app.service.provider.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceImplTest {

    @Mock
    private OrderRepository ordersRepository;
    @Mock
    private ServiceTools serviceTools;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void should_successfully_find_most_popular_customer() {
        // given
        when(ordersRepository.findAll()).thenReturn(createOrders());
        when(serviceTools.findCustomerByOrder(any())).thenReturn(createCustomer());

        // when
        Customer actualCustomer = customerService.findMostPopularCustomer();

        // then
        assertEquals(1, actualCustomer.getId());
        assertEquals("Harry", actualCustomer.getName());
        assertEquals("Potter", actualCustomer.getSurname());
        assertEquals(Integer.valueOf(18), actualCustomer.getAge());
        assertEquals(1, actualCustomer.getCountryId());
    }
}