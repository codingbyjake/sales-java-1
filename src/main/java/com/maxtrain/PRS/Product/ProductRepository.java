package com.maxtrain.PRS.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	Iterable<Product> getByVendorId(int vendorId);

}
