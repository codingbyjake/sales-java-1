package com.maxtrain.PRS.RequestLine;

//Imports
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestLineController {
	
	@Autowired
	private RequestLineRepository reqlineRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<RequestLine>> getAllRequestLines(){
		Iterable<RequestLine> requestlines = reqlineRepo.findAll();
		return new ResponseEntity<Iterable<RequestLine>>(requestlines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<RequestLine> getRequestLine(@PathVariable int id){
		Optional<RequestLine> requestline = reqlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RequestLine>(requestline.get(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<RequestLine> putRequestLine(@PathVariable int id, @RequestBody RequestLine requestline){
		if(requestline.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		RequestLine updatedRequestLine = reqlineRepo.save(requestline);
		return new ResponseEntity<RequestLine>(updatedRequestLine, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RequestLine> postRequestLine(@RequestBody RequestLine requestline){
		RequestLine newRequestLine = reqlineRepo.save(requestline);
		return new ResponseEntity<RequestLine>(newRequestLine, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<RequestLine> deleteRequestLine(@PathVariable int id){
		Optional<RequestLine> requestline = reqlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqlineRepo.delete(requestline.get());
		return new ResponseEntity<RequestLine>(requestline.get(), HttpStatus.GONE);
	}
		

}
