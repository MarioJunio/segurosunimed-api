package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerFilter;
import com.example.api.service.CustomerService;
import com.example.api.web.request.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Page<Customer>> findAll(@RequestParam(defaultValue = "") String name,
                                                  @RequestParam(defaultValue = "") String email,
                                                  @RequestParam(defaultValue = "") String gender,
                                                  @RequestParam(defaultValue = "") String city,
                                                  @RequestParam(defaultValue = "") String state,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(service.findAll(CustomerFilter.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .city(city)
                .state(state)
                .pageSize(pageSize)
                .currentPage(page)
                .build()));
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity
                .created(null)
                .body(service.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(service.save(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam List<Long> ids) {
        service.delete(ids);
    }

}
