package com.example.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerFilter {

    private String name;
    private String email;
    private String gender;
    private String city;
    private String state;

    private int pageSize;
    private int currentPage;
}
