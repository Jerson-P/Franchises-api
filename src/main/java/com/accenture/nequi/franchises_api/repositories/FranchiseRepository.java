package com.accenture.nequi.franchises_api.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.accenture.nequi.franchises_api.entities.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {
	
	Mono<Franchise> findByName(String name);

}
