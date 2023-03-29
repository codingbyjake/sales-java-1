package com.maxtrain.PRS.RequestLine;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maxtrain.PRS.Product.Product;
import com.maxtrain.PRS.Request.Request;

// Imports
import jakarta.persistence.*;

@Entity
@Table(name="RequestLines")
public class RequestLine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int Quantity = 1;
	
	// FK's
	@JsonManagedReference
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId", columnDefinition="int")
	private Request request;
	@ManyToOne(optional=false)
	@JoinColumn(name="productId", columnDefinition="int")
	private Product product;
	
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

}
