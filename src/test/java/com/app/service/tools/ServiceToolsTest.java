package com.app.service.tools;

import com.app.exceptions.MyException;
import com.app.model.*;
import com.app.repository.category.CategoryRepository;
import com.app.repository.country.CountryRepository;
import com.app.repository.customer.CustomerRepository;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServiceToolsTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProducerRepository producerRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ServiceTools serviceTools;

    @Test
    void should_successfully_find_category_by_id() {
        // given
        when(categoryRepository.findOneById(any())).thenReturn(Optional.of(createCategory()));

        // when
        Category actualCategory = serviceTools.findCategoryByProduct(createProduct());

        // then
        assertEquals("Alcohol", actualCategory.getName());
    }

    @Test
    void should_throw_exception_when_category_does_not_exist() {
        // given

        // when
        assertThrows(MyException.class, () -> serviceTools.findCategoryByProduct(new Product()));

        // then
    }

    @Test
    void should_successfully_find_country_by_id() {
        // given
        when(countryRepository.findOneById(any())).thenReturn(Optional.of(createCountry()));

        // when
        Country actualCountry = serviceTools.findCountryByProduct(createProduct());

        // then
        assertEquals("Poland", actualCountry.getName());
    }

    @Test
    void should_throw_exception_when_country_does_not_exist() {
        // given

        // when
        assertThrows(MyException.class, () -> serviceTools.findCountryByProduct(new Product()));

        // then
    }

    @Test
    void should_successfully_find_customer_by_id() {
        // given
        when(customerRepository.findOneById(any())).thenReturn(Optional.of(createCustomer()));

        // when
        Customer actualCustomer = serviceTools.findCustomerByOrder(createOrder());

        // then
        assertEquals("Harry", actualCustomer.getName());
        assertEquals("Potter", actualCustomer.getSurname());
        assertEquals(Integer.valueOf(18), actualCustomer.getAge());
    }

    @Test
    void should_throw_exception_when_customer_does_not_exist() {
        // given

        // when
        assertThrows(MyException.class, () -> serviceTools.findCustomerByOrder(new Order()));

        // then
    }

    @Test
    void should_successfully_find_producer_by_id() {
        // given
        when(producerRepository.findOneById(any())).thenReturn(Optional.of(createProducer()));

        // when
        Producer actualProducer = serviceTools.findProducerByProduct(createProduct());

        // then
        assertEquals("Adidas", actualProducer.getName());

    }

    @Test
    void should_throw_exception_when_producer_does_not_exist() {
        // given

        // when
        assertThrows(MyException.class, () -> serviceTools.findProducerByProduct(new Product()));

        // then
    }

    @Test
    void should_successfully_find_product_by_id() {
        // given
        when(productRepository.findOneById(any())).thenReturn(Optional.of(createProduct()));

        // when
        Product actualProduct = serviceTools.findProductByOrder(createOrder());

        // then
        assertEquals("Jack Daniels", actualProduct.getName());
        assertEquals(BigDecimal.valueOf(100), actualProduct.getPrice());
    }

    @Test
    void should_throw_exception_when_product_does_not_exist() {
        // given

        // when
        assertThrows(MyException.class, () -> serviceTools.findProductByOrder(new Order()));

        // then
    }

    private Product createProduct() {
        return Product.builder().id(1L).name("Jack Daniels").price(BigDecimal.valueOf(100)).countryId(1L).producerId(1L)
                .categoryId(1L).build();
    }

    private Category createCategory() {
        return Category.builder().id(1L).name("Alcohol").build();
    }

    private Country createCountry() {
        return Country.builder().id(1L).name("Poland").build();
    }

    private Order createOrder() {
        return Order.builder().build();
    }

    private Customer createCustomer() {
        return Customer.builder().name("Harry").surname("Potter").age(18).build();
    }

    private Producer createProducer() {
        return Producer.builder().name("Adidas").build();
    }
}