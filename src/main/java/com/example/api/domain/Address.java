package com.example.api.domain;

import com.example.api.external.response.ViaCepResponse;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String cep;

    private String street;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String ibge;

    private String ddd;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public static Address fromViaCep(final ViaCepResponse viaCep) {
        return Address.builder()
                .cep(viaCep.getCep())
                .street(viaCep.getLogradouro())
                .complement(viaCep.getComplemento())
                .neighborhood(viaCep.getBairro())
                .city(viaCep.getLocalidade())
                .state(viaCep.getUf())
                .ibge(viaCep.getIbge())
                .ddd(viaCep.getDdd())
                .build();
    }
}
