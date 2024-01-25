package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.CustomerFilter;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.external.client.ViaCepClient;
import com.example.api.external.response.ViaCepResponse;
import com.example.api.repository.CustomerRepository;
import com.example.api.web.request.CustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final ViaCepClient viaCepClient;
    private final CustomerRepository repository;
    private final ModelMapper modelMapper;

    public Customer save(CustomerRequest customerRequest) {
        final Customer customer = modelMapper.map(customerRequest, Customer.class);

        customer.setAddresses(getAddressesByCeps(customerRequest.getCeps(), customer));

        return repository.save(customer);
    }

    public Customer save(Long id, CustomerRequest customerRequest) {
        final Customer customer = findById(id);

        BeanUtils.copyProperties(customerRequest, customer);

        customer.getAddresses().clear();
        customer.getAddresses().addAll(getAddressesByCeps(customerRequest.getCeps(), customer));

        return repository.save(customer);
    }

    public Page<Customer> findAll(CustomerFilter filter) {
        return repository
                .findDistinctByNameContainingAndEmailContainingAndGenderContainingAndAddressesCityContainingAndAddressesStateContaining(
                        filter.getName(),
                        filter.getEmail(),
                        filter.getGender(),
                        filter.getCity(),
                        filter.getState(),
                        PageRequest.of(filter.getCurrentPage(), filter.getPageSize()));
    }

    public Customer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado para o id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void delete(List<Long> ids) {
        repository.deleteAllById(ids);
    }


    private List<Address> getAddressesByCeps(List<String> ceps, Customer customer) {
        return ceps
                .parallelStream().map(cep -> {
                    final ViaCepResponse viaCepResponse = viaCepClient.getAddress(cep);
                    final Address address = Address.fromViaCep(viaCepResponse);

                    address.setCustomer(customer);

                    return address;
                }).collect(Collectors.toList());
    }

}
