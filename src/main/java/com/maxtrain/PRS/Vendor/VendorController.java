package com.maxtrain.PRS.Vendor;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.maxtrain.PRS.Po.Po;
import com.maxtrain.PRS.Poline.Poline;
import com.maxtrain.PRS.Product.Product;
import com.maxtrain.PRS.Product.ProductRepository;
import com.maxtrain.PRS.Request.RequestRepository;
import com.maxtrain.PRS.RequestLine.RequestLine;
import com.maxtrain.PRS.RequestLine.RequestLineRepository;
import com.maxtrain.PRS.Request.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository venRepo;
	
	@Autowired
	private RequestRepository reqRepo;
	
	@Autowired
	private RequestLineRepository reqlineRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
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
	
	// *************** Additional Create Po Method
	@GetMapping("po/{vendorId}")
	public ResponseEntity<Po> CreatePo(@PathVariable int vendorId){
		// Find Vendor by passed in vendorId and check if it exists(is empty)
		Optional<Vendor> vendor = venRepo.findById(vendorId);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Create a new Po instance
		Po po = new Po();
		// Set Po Vendor
		po.setVendor(vendor.get());
		// Get Products of the Vendor
		Iterable<Product> products = prodRepo.getByVendorId(vendorId);
		// Create a HashMap Dictionary of Products????                                   <<<<<<<<<<???????????????
		HashMap<Integer, Product> dictProducts = new HashMap<Integer, Product>();
		for(Product product : products) {
			if(product.getVendor().getId() == vendorId) {
				dictProducts.put(product.getId(), product);
			}
		}
		
		// Get all Requests with Status = APPROVED and put in an iterable list		
		Iterable<Request> requests = reqRepo.findByStatus("APPROVED");
			for(Request req : requests) {
				// Get all Requestlines associated with approved requests
				Iterable<RequestLine> associatedReqlines = reqlineRepo.findByRequestId(req.getId());
				for (RequestLine reqline : associatedReqlines) {
					int prodId = reqline.getProduct().getId();
					if(dictProducts.containsKey(prodId)) {
						Poline poline = new Poline();
						poline.setProduct(reqline.getProduct().getName());
						poline.setQuantity(reqline.getQuantity());
						poline.setPrice(reqline.getProduct().getPrice());
						poline.setLineTotal(poline.getQuantity()*poline.getPrice());
						po.setPoTotal(po.getPoTotal() + poline.getLineTotal());
						//po.setPolines(po.getPolines().add(poline));
						po.getPolines().add(poline);
					}
				}				
			}	
			return new ResponseEntity<Po>(po, HttpStatus.OK);
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
		return new ResponseEntity<Vendor>(newVendor, HttpStatus.CREATED);	
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
