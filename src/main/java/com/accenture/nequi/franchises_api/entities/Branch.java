package com.accenture.nequi.franchises_api.entities;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Branch {
	
	@Id
	private String id;
	
    private String name;
    
    private List<Product> products;
}