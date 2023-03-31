package com.maxtrain.PRS.Request;

//Imports
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	

	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getAllRequests(){
		Iterable<Request> requests = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id){
		Optional<Request> request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	// **************** Additional Get All Requests with Status REVIEW and not owned by current User Method
	@GetMapping("reviews/{id}")
	public ResponseEntity<Iterable<Request>> getReviews(@PathVariable int id){
		//Optional<Iterable<Request>> requests = reqRepo.findByStatus("REVIEW").Where(reqRepo.findAll().getUser() != id);
		//Optional<Iterable<Request>> requests = " SELECT * FROM Requests WHERE status = "REVIEW" && "
		
		Optional<Iterable<Request>> requests = reqRepo.findByStatusAndUserIdNot("REVIEW", id);		
		return new ResponseEntity<Iterable<Request>>(requests.get(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Request> putRequest(@PathVariable int id, @RequestBody Request request){
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Request updatedRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(updatedRequest, HttpStatus.OK);
	}
	
	// **************** Additional Conditional Set to Review or Approved Method
	@PutMapping("review/{id}")
	public ResponseEntity<Request> review(@PathVariable int id, @RequestBody Request request){
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(request.getTotal()<= 50) {
			request.setStatus("APPROVED");
			}else {
			request.setStatus("REVIEW");
			}
		Request updatedRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(updatedRequest, HttpStatus.OK);
	}
	
	// **************** Additional Set to Approved Method
	@PutMapping("approve/{id}")
	public ResponseEntity<Request> approve(@PathVariable int id, @RequestBody Request request){
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		request.setStatus("APPROVED");
		Request updatedRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(updatedRequest, HttpStatus.OK);
	}
	
	// **************** Additional Set to Rejected Method
	@PutMapping("reject/{id}")
	public ResponseEntity<Request> reject(@PathVariable int id, @RequestBody Request request){
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		request.setStatus("REJECTED");
		Request updatedRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(updatedRequest, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request){
		Request newRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	//public ResponseEntity<Request> deleteRequest(@PathVariable int id){
	public ResponseEntity deleteRequest(@PathVariable int id){
		Optional<Request> request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		//Request deletedRequest = request.get();
		reqRepo.delete(request.get());
		//return new ResponseEntity<Request>(request.get(), HttpStatus.GONE);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}




