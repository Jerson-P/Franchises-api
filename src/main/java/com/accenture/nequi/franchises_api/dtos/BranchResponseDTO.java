package com.accenture.nequi.franchises_api.dtos;

import java.util.List;

import lombok.Data;

@Data
public class BranchResponseDTO {
	private String id;
    private String name;
    private List<ProductResponseDTO> products;
}