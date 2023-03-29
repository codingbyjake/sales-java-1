package com.maxtrain.PRS.Request;

import java.util.List;

import com.maxtrain.PRS.RequestLine.RequestLine;
import com.maxtrain.PRS.User.User;

// Imports
import jakarta.persistence.*;


@Entity
@Table(name="Requests")
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=80, nullable = false)
	private String description;
	@Column(length=80, nullable = false)
	private String justification;
	@Column(length=80, nullable = true)
	private String rejectionReason;
	@Column(length=20, nullable = false)
	private String DeliveryMode = "Pickup";
	@Column(length=10, nullable = false)
	private String status = "NEW";
	@Column(columnDefinition="decimal(11,2) NOT NULL DEFAULT 0")
	private double total;
	
	// FK's
	@ManyToOne(optional=false)
	@JoinColumn(name="userId", columnDefinition="int")
	private User user;
	
	// Virtual Collection
	@OneToMany(mappedBy="request")
	private List<RequestLine> requestLines;

	
	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getDeliveryMode() {
		return DeliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		DeliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RequestLine> getRequestLines() {
		return requestLines;
	}

	public void setRequestLines(List<RequestLine> requestLines) {
		this.requestLines = requestLines;
	}
	
	

}
