package com.accenture.nequi.franchises_api.dtos;

import lombok.Data;

@Data
public class ProductResponseDTO {
	private String id;
    private String name;
    private int stock;
}
