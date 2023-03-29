package com.maxtrain.PRS.Vendor;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository venRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getAllVendors(){
		Iterable<Vendor> vendors = venRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id){
		Optional<Vendor> vendor = venRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Vendor> putVendor(@PathVariable int id, @RequestBody Vendor vendor){
		if(vendor.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Vendor updatedVendor = venRepo.save(vendor);
		return new ResponseEntity<Vendor>(updatedVendor, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor){
		Vendor newVendor = venRepo.save(vendor);
		return new ResponseEntity<Vendor>(newVendor, HttpStatus.OK);	
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Vendor> deleteVendor(@PathVariable int id){
		Optional<Vendor> vendor = venRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		venRepo.delete(vendor.get());
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.GONE);
	}
	
}
