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
	
	@PutMapping("{id}")
	public ResponseEntity<Request> putRequest(@PathVariable int id, @RequestBody Request request){
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Request updatedRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(updatedRequest, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request){
		Request newRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Request> deleteRequest(@PathVariable int id){
		Optional<Request> request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.delete(request.get());
		return new ResponseEntity<Request>(request.get(), HttpStatus.GONE);
	}

}
