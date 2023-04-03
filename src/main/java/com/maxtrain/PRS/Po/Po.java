package com.maxtrain.PRS.Po;

import java.util.ArrayList;
import java.util.List;

import com.maxtrain.PRS.Poline.Poline;
import com.maxtrain.PRS.Vendor.Vendor;

public class Po {	
	
	private Vendor vendor;
	private List<Poline> polines = new ArrayList<Poline>();
	private double poTotal;

	
	// Getters & Setters
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public List<Poline> getPolines() {
		return polines;
	}
	public void setPolines(List<Poline> polines) {
		this.polines = polines;
	}
	public double getPoTotal() {
		return poTotal;
	}
	public void setPoTotal(double poTotal) {
		this.poTotal = poTotal;
	}
	
	


}
