package com.maxtrain.PRS.RequestLine;

import org.springframework.data.repository.CrudRepository;

public interface RequestLineRepository extends CrudRepository<RequestLine, Integer> {
	Iterable<RequestLine> findByRequestId(int requestId);
}
