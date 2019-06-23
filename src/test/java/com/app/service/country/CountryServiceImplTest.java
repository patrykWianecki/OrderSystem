package com.app.service.country;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.app.model.Country;
import com.app.repository.order.OrderRepository;
import com.app.service.tools.ServiceTools;

import static com.app.service.provider.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CountryServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ServiceTools serviceTools;

    @InjectMocks
    private CountryServiceImpl countryService;

    // TODO
    @Test
    void should_successfully_find_sorted_countries_with_customer_who_spend_most() {
        // given

        // when

        // then

    }

    @Test
    void should_successfully_find_most_popular_country() {
        // given
        when(orderRepository.findAll()).thenReturn(createOrders());
        when(serviceTools.findProductByOrder(any())).thenReturn(createProduct());
        when(serviceTools.findCountryByProduct(any())).thenReturn(createCountry());

        // when
        Country country = countryService.findMostPopularCountry();

        // then
        assertEquals(1, country.getId());
        assertEquals("Poland", country.getName());
    }
}