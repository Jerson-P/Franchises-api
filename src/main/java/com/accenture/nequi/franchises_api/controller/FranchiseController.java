package com.accenture.nequi.franchises_api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.nequi.franchises_api.dtos.FranchiseRequestDTO;
import com.accenture.nequi.franchises_api.dtos.FranchiseResponseDTO;
import com.accenture.nequi.franchises_api.dtos.ProductRequestDTO;
import com.accenture.nequi.franchises_api.entities.Branch;
import com.accenture.nequi.franchises_api.entities.Product;
import com.accenture.nequi.franchises_api.services.FranchiseService;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

    // Endpoint para consultar todas las franquicias
    @GetMapping
    public Flux<FranchiseResponseDTO> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    // Endpoint para consultar una franquicia por id
    @GetMapping("/{id}")
    public Mono<FranchiseResponseDTO> getFranchiseById(@PathVariable String id) {
        return franchiseService.getFranchiseById(id);
    }
    
    // Endpoint para consultar una franquicia por nombre
    @GetMapping("/name/{name}")
    public Mono<FranchiseResponseDTO> getFranchiseByName(@PathVariable String name) {
        return franchiseService.getFranchiseByName(name);
    }

    // Endpoint para crear una franquicia
    @PostMapping
    public Mono<FranchiseResponseDTO> createFranchise(@RequestBody FranchiseRequestDTO franchiseRequest) {
        return franchiseService.createFranchise(franchiseRequest);
    }
    // Endpoint para eliminar una franquicia por id
    @DeleteMapping("/{id}")
    public Mono<Void> deleteFranchise(@PathVariable String id) {
        return franchiseService.deleteFranchise(id);
    }

    // Endpoint para actualizar una franquicia por id
    @PutMapping("/{id}")
    public Mono<FranchiseResponseDTO> updateFranchise(
            @PathVariable String id, 
            @RequestBody FranchiseRequestDTO franchiseRequest) {
        return franchiseService.updateFranchise(id, franchiseRequest);
    }
    // Endpoint para agregar una sucursal a una franquicia
    @PostMapping("/{franchiseId}/branches")
    public Mono<FranchiseResponseDTO> addBranchToFranchise(
            @PathVariable String franchiseId, 
            @RequestBody Branch branch) {
        return franchiseService.addBranchToFranchise(franchiseId, branch);
    }
    
    // Endpoint para crear un producto a una sucursal
    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    public Mono<FranchiseResponseDTO> addProductToBranch(
            @PathVariable String franchiseId, 
            @PathVariable String branchId, 
            @RequestBody ProductRequestDTO productRequest) {
        return franchiseService.addProductToBranch(franchiseId, branchId, productRequest);
    }
    
    // Endpoint para eliminar un producto de una sucursal
    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<FranchiseResponseDTO> removeProductFromBranch(
            @PathVariable String franchiseId, 
            @PathVariable String branchId, 
            @PathVariable String productId) {
        return franchiseService.removeProductFromBranch(franchiseId, branchId, productId);
    }

    // Endpoint para actualizar el stock de un producto
    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<FranchiseResponseDTO> updateProductStock(
            @PathVariable String franchiseId, 
            @PathVariable String branchId, 
            @PathVariable String productId, 
            @RequestBody JsonNode body) {
    	Integer newStock = body.get("stock").asInt();
        return franchiseService.updateProductStock(franchiseId, branchId, productId, newStock);
    }
    
    // Endpoint para traer el producto con más stock
    @GetMapping("/{franchiseId}/branches/{branchId}/products/max-stock")
    public Mono<Product> getProductWithMaxStock(
            @PathVariable String franchiseId, 
            @PathVariable String branchId) {
        return franchiseService.getProductWithMaxStock(franchiseId, branchId);
    }
    
    // Actualizar el nombre de una franquicia
    @PutMapping("/{id}/name")
    public Mono<FranchiseResponseDTO> updateFranchiseName(
            @PathVariable String id, 
            @RequestBody JsonNode body) {
    	String newName = body.get("name").asText();
        return franchiseService.updateFranchiseName(id, newName);
    }

    // Actualizar el nombre de una sucursal
    @PutMapping("/{franchiseId}/branches/{branchId}/name")
    public Mono<FranchiseResponseDTO> updateBranchName(
            @PathVariable String franchiseId, 
            @PathVariable String branchId, 
            @RequestBody String newBranchName) {
        return franchiseService.updateBranchName(franchiseId, branchId, newBranchName);
    }

    // Actualizar el nombre de un producto
    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/name")
    public Mono<FranchiseResponseDTO> updateProductName(
            @PathVariable String franchiseId, 
            @PathVariable String branchId, 
            @PathVariable String productId, 
            @RequestBody String newProductName) {
        return franchiseService.updateProductName(franchiseId, branchId, productId, newProductName);
    }
}
