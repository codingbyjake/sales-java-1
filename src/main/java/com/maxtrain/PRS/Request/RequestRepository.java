package com.maxtrain.PRS.Request;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer>{
	Optional<Iterable<Request>> findByStatus(String status);
	Optional<Iterable<Request>> findByUserId(int id);
	Optional<Iterable<Request>> findByStatusAndUserIdNot(String status, int id);
}


