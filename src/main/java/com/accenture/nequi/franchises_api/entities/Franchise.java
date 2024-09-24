package com.accenture.nequi.franchises_api.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "franchises")
public class Franchise {
	
    @Id
    private String id;
    
    private String name;
    
    private List<Branch> branches;
    
}
