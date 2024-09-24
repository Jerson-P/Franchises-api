package com.accenture.nequi.franchises_api.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.accenture.nequi.franchises_api.dtos.BranchRequestDTO;
import com.accenture.nequi.franchises_api.dtos.BranchResponseDTO;
import com.accenture.nequi.franchises_api.dtos.FranchiseRequestDTO;
import com.accenture.nequi.franchises_api.dtos.FranchiseResponseDTO;
import com.accenture.nequi.franchises_api.dtos.ProductRequestDTO;
import com.accenture.nequi.franchises_api.dtos.ProductResponseDTO;
import com.accenture.nequi.franchises_api.entities.Branch;
import com.accenture.nequi.franchises_api.entities.Franchise;
import com.accenture.nequi.franchises_api.entities.Product;

@Component
public class FranchiseMapper {

    // Método para mapear DTO a entidad
    public Franchise mapToEntityFranchise(FranchiseRequestDTO dto) {
        Franchise franchise = new Franchise();
        franchise.setName(dto.getName());
        franchise.setBranches(dto.getBranches().stream()
                .map(this::mapToEntityBranch)
                .collect(Collectors.toList()));
        return franchise;
    }

    public Branch mapToEntityBranch(BranchRequestDTO dto) {
        Branch branch = new Branch();
        branch.setName(dto.getName());
        branch.setProducts(dto.getProducts().stream()
                .map(this::mapToEntityProduct)
                .collect(Collectors.toList()));
        return branch;
    }

    public Product mapToEntityProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setStock(dto.getStock());
        return product;
    }

    // Método para mapear entidad a DTO
    public FranchiseResponseDTO mapToResponseDTOFrancise(Franchise franchise) {
        FranchiseResponseDTO dto = new FranchiseResponseDTO();
        dto.setId(franchise.getId());
        dto.setName(franchise.getName());
        dto.setBranches(franchise.getBranches().stream()
                .map(this::mapToResponseDTOBranch)
                .collect(Collectors.toList()));
        return dto;
    }

    public BranchResponseDTO mapToResponseDTOBranch(Branch branch) {
        BranchResponseDTO dto = new BranchResponseDTO();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setProducts(branch.getProducts().stream()
                .map(this::mapToResponseDTOProduct)
                .collect(Collectors.toList()));
        return dto;
    }

    public ProductResponseDTO mapToResponseDTOProduct(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        return dto;
    }
}
