package com.example.api.external.client;

import com.example.api.external.response.ViaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ViaCepClientFallback implements ViaCepClient {

    @Override
    public ViaCepResponse getAddress(String cep) {
        log.info("ViaCepClient fallback for cep: {}", cep);

        return ViaCepResponse.builder()
                .complemento("Endereço não encontrado")
                .build();
    }
}
