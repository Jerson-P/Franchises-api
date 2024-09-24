package com.accenture.nequi.franchises_api.dtos;

import java.util.List;

import lombok.Data;

@Data
public class FranchiseRequestDTO {
    private String name;
    private List<BranchRequestDTO> branches;
}