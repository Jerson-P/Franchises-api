package com.accenture.nequi.franchises_api.dtos;

import java.util.List;

import lombok.Data;

@Data
public class BranchRequestDTO {
    private String name;
    private List<ProductRequestDTO> products;
}
