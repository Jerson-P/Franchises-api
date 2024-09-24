package com.accenture.nequi.franchises_api.entities;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Product {
	
	@Id
	private String id;
	
    private String name;
    
    private int stock;
    
}