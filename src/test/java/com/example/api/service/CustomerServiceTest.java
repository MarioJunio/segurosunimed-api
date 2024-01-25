package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.CustomerFilter;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.external.client.ViaCepClient;
import com.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static com.example.api.service.Mocks.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private CustomerRepository repository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void saveTest() {
        when(viaCepClient.getAddress("38500000")).thenReturn(viaCepResponse);

        when(repository.save(Mockito.any(Customer.class))).thenReturn(customer);

        final Customer customerSaved = customerService.save(customerRequest);

        assertEquals(customer, customerSaved);
    }

    @Test
    public void saveCepNotFoundTest() {
        when(viaCepClient.getAddress(Mockito.anyString())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> {
            viaCepClient.getAddress(Mockito.anyString());
        });
    }

    @Test
    public void updateTest() {
        customer.getAddresses().add(Address.fromViaCep(viaCepResponse));

        when(viaCepClient.getAddress("38500000")).thenReturn(viaCepResponse);

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));

        when(repository.save(Mockito.any(Customer.class))).thenReturn(customer);

        final Customer savedCustomer = customerService.save(1L, customerRequest);

        assertEquals(customer, savedCustomer);
    }

    @Test
    public void findAllWithFilterTest() {
        final Page<Customer> page = new PageImpl<>(Arrays.asList(customer), PageRequest.of(1, 10), 1);

        when(repository.findDistinctByNameContainingAndEmailContainingAndGenderContainingAndAddressesCityContainingAndAddressesStateContaining(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any()))
                .thenReturn(page);

        final Page<Customer> pageCustomer = customerService.findAll(CustomerFilter.builder()
                .name("Name")
                .city("")
                .email("")
                .state("")
                .gender("")
                .pageSize(10)
                .currentPage(1)
                .build());

        assertEquals(page, pageCustomer);
    }

    @Test
    public void findByIdTest() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));

        final Customer customerFound = customerService.findById(1L);

        assertEquals(customer, customerFound);
    }

    @Test
    public void findByIdNotFoundTest() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1L));
    }

    @Test
    public void deleteTest() {
        doNothing().when(repository).deleteById(Mockito.anyLong());

        customerService.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }
}
