package com.example.api.external.client;

import com.example.api.external.response.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep-client", url = "https://viacep.com.br/ws", fallback = ViaCepClientFallback.class)
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    ViaCepResponse getAddress(@PathVariable("cep") String cep);
}
