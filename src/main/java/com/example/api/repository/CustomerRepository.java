package com.example.api.repository;

import com.example.api.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Page<Customer> findDistinctByNameContainingAndEmailContainingAndGenderContainingAndAddressesCityContainingAndAddressesStateContaining(@Param("name") String name,
                                                                                                                                  @Param("email") String email,
                                                                                                                                  @Param("gender") String gender,
                                                                                                                                  @Param("city") String city,
                                                                                                                                  @Param("state") String state,
                                                                                                                                  Pageable pageable);

}
