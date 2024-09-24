package com.accenture.nequi.franchises_api.services;

import java.util.Comparator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.nequi.franchises_api.dtos.FranchiseRequestDTO;
import com.accenture.nequi.franchises_api.dtos.FranchiseResponseDTO;
import com.accenture.nequi.franchises_api.dtos.ProductRequestDTO;
import com.accenture.nequi.franchises_api.entities.Branch;
import com.accenture.nequi.franchises_api.entities.Franchise;
import com.accenture.nequi.franchises_api.entities.Product;
import com.accenture.nequi.franchises_api.mapper.FranchiseMapper;
import com.accenture.nequi.franchises_api.repositories.FranchiseRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseService {

	@Autowired
	private FranchiseRepository franchiseRepository;

	@Autowired
	private FranchiseMapper franchiseMapper;

	public Flux<FranchiseResponseDTO> getAllFranchises() {
		return franchiseRepository.findAll().map(franchiseMapper::mapToResponseDTOFrancise)
				.onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to fetch franchises", error));
	}

	public Mono<FranchiseResponseDTO> getFranchiseById(String id) {
		return franchiseRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.map(franchiseMapper::mapToResponseDTOFrancise)
				.onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to fetch franchise", error));
	}

	public Mono<FranchiseResponseDTO> getFranchiseByName(String name) {
		return franchiseRepository.findByName(name)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.map(franchiseMapper::mapToResponseDTOFrancise)
				.onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to fetch franchise by name", error));
	}

	public Mono<FranchiseResponseDTO> createFranchise(FranchiseRequestDTO franchiseRequest) {
		Franchise franchise = franchiseMapper.mapToEntityFranchise(franchiseRequest);
		return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise)
				.onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to create franchise", error));
	}

	public Mono<Void> deleteFranchise(String id) {
		return franchiseRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> franchiseRepository.deleteById(id))
				.onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to delete franchise", error));
	}

	public Mono<FranchiseResponseDTO> updateFranchise(String id, FranchiseRequestDTO franchiseRequest) {
		return franchiseRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(existingFranchise -> {
					Franchise updatedFranchise = franchiseMapper.mapToEntityFranchise(franchiseRequest);
					existingFranchise.setName(updatedFranchise.getName());
					existingFranchise.setBranches(updatedFranchise.getBranches());
					return franchiseRepository.save(existingFranchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to update franchise", error));
	}

	public Mono<FranchiseResponseDTO> addProductToBranch(String franchiseId, String branchId,
			ProductRequestDTO productRequest) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					Branch branch = franchise.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

					Product product = franchiseMapper.mapToEntityProduct(productRequest);
					if (product.getId() == null || product.getId().isEmpty()) {
						product.setId(new ObjectId().toString());
					}
					branch.getProducts().add(product);

					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to add product to branch", error));
	}

	public Mono<FranchiseResponseDTO> removeProductFromBranch(String franchiseId, String branchId, String productId) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					Branch branch = franchise.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

					boolean removed = branch.getProducts().removeIf(product -> product.getId().equals(productId));
					if (!removed) {
						return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
					}

					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to remove product from branch", error));
	}

	public Mono<FranchiseResponseDTO> addBranchToFranchise(String franchiseId, Branch branch) {
		if (branch.getId() == null || branch.getId().isEmpty()) {
			branch.setId(new ObjectId().toString());
		}
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					franchise.getBranches().add(branch);
					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to add branch to franchise", error));
	}

	public Mono<FranchiseResponseDTO> updateProductStock(String franchiseId, String branchId, String productId,
			int newStock) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					Branch branch = franchise.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

					Product product = branch.getProducts().stream().filter(p -> p.getId().equals(productId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

					product.setStock(newStock);
					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to update product stock", error));
	}

	public Mono<Product> getProductWithMaxStock(String franchiseId, String branchId) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					Branch branch = franchise.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

					return Mono.just(branch.getProducts().stream().max(Comparator.comparingInt(Product::getStock))
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found")));
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to get product with max stock", error));
	}

	public Mono<FranchiseResponseDTO> updateFranchiseName(String franchiseId, String newName) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					franchise.setName(newName);
					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to update franchise name", error));
	}

	public Mono<FranchiseResponseDTO> updateBranchName(String franchiseId, String branchId, String newBranchName) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					Branch branch = franchise.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

					branch.setName(newBranchName);
					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to update branch name", error));
	}

	public Mono<FranchiseResponseDTO> updateProductName(String franchiseId, String branchId, String productId,
			String newProductName) {
		return franchiseRepository.findById(franchiseId)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franchise not found")))
				.flatMap(franchise -> {
					Branch branch = franchise.getBranches().stream().filter(b -> b.getId().equals(branchId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

					Product product = branch.getProducts().stream().filter(p -> p.getId().equals(productId)).findFirst()
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

					product.setName(newProductName);
					return franchiseRepository.save(franchise).map(franchiseMapper::mapToResponseDTOFrancise);
				}).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to update product name", error));
	}

}
