package com.maxtrain.PRS.RequestLine;

//Imports
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.maxtrain.PRS.Request.Request;
import com.maxtrain.PRS.Request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestLineController {
	
	@Autowired
	private RequestLineRepository reqlineRepo;
	
	@Autowired
	private RequestRepository reqRepo;
	
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
		RecalculateRequestTotal(id);													//  >>>>>>>>>>>>>>>  Inserted Call to RecalculateRequestTotal
		return new ResponseEntity<RequestLine>(updatedRequestLine, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RequestLine> postRequestLine(@RequestBody RequestLine requestline){
		RequestLine newRequestLine = reqlineRepo.save(requestline);
		RecalculateRequestTotal(newRequestLine.getRequest().getId());						//  >>>>>>>>>>>>>>>  Inserted Call to RecalculateRequestTotal
		return new ResponseEntity<RequestLine>(newRequestLine, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<RequestLine> deleteRequestLine(@PathVariable int id){
		Optional<RequestLine> requestline = reqlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		RequestLine goneRequestLine = requestline.get();
		reqlineRepo.delete(requestline.get());
		RecalculateRequestTotal(goneRequestLine.getRequest().getId());						//  >>>>>>>>>>>>>>>  Inserted Call to RecalculateRequestTotal
		return new ResponseEntity<RequestLine>(requestline.get(), HttpStatus.GONE);
	}
	
	// ************* Private Additional Recalculate Request Total Method
	private boolean RecalculateRequestTotal(int id) {
		Optional<Request> request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return false;
		}
		Request foundRequest = request.get();
		Iterable<RequestLine> associatedRequestLines = reqlineRepo.findByRequestId(id);
		double newTotal = 0;
		for(RequestLine rl : associatedRequestLines) {
			newTotal += rl.getQuantity()*rl.getProduct().getPrice();
		}
		foundRequest.setTotal(newTotal);
		reqRepo.save(foundRequest);
		return true;
	}
	
	// ************* Private Additional Recalculate Request Total Method blind attempt
//	private Request RecalculateRequestTotal(int id) {
//		Optional<RequestLine> reqline = reqlineRepo.findById(id);
//		Optional<Request> req = reqRepo.findById(reqline.get().getRequest().getId());
//		req.get().setTotal(req.get().getTotal() + reqline.get().getQuantity()*reqline.get().getProduct().getPrice());
//		Request updatedRequest = reqRepo.save(req.get());
//		return updatedRequest;
//	}
		

}
