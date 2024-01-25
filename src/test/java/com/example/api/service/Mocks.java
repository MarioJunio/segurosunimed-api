package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.external.response.ViaCepResponse;
import com.example.api.web.request.CustomerRequest;
import org.assertj.core.util.Lists;

import java.util.ArrayList;

public class Mocks {

    public static final Address address = Address.builder()
            .cep("38500000")
            .city("Monte Carmelo")
            .state("MG")
            .build();

    public static final ViaCepResponse viaCepResponse = ViaCepResponse.builder().cep("38500000").localidade("Monte Carmelo").uf("MG").build();

    public static final CustomerRequest customerRequest = CustomerRequest.builder().name("Name sample").email("name.sample@gmail.com").gender("M").ceps(Lists.list("38500000")).build();

    public static final Customer customer = Customer.builder().id(1L).name("Name sample").email("name.sample@gmail.com").gender("M").addresses(new ArrayList<>()).build();
}
