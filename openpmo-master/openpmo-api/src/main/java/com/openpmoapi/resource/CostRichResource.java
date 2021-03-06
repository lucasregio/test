package com.openpmoapi.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openpmoapi.event.RecursoCriadoEvent;
import com.openpmoapi.model.CostRich;
import com.openpmoapi.repository.CostRichRepository;
import com.openpmoapi.service.CostRichService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/costrich")
@Api(value = "/api/cost",  tags = "Cost",description=" ")
public class CostRichResource {
	
	@Autowired
	private CostRichRepository costRichRepository;
	
	
	@Autowired
	private CostRichService costRichService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * This is method delete one CostRich
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		costRichRepository.deleteById(id);
	}
	
	
	
	/**
	 * This is method update costRich
	 */
	@PutMapping("/{id}")
	public ResponseEntity<CostRich> update(@PathVariable Long id, @Valid @RequestBody CostRich costRich) {
		CostRich costRichSalvo = costRichService.update(id, costRich);
		return ResponseEntity.ok(costRichSalvo);
	}
	
	
	/**
		This is method save CostRich
	 */
	@PostMapping
	public ResponseEntity<CostRich> save(@Valid @RequestBody CostRich costRich, HttpServletResponse response) {
		CostRich costRichSalvo = costRichRepository.save(costRich);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, costRichSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(costRichRepository.save(costRich));
	}
	
	
	/**
	 * This is method find by all CostRich
	 */
	@GetMapping
	public Iterable<CostRich> findByAll() {
		 return costRichRepository.findAll(2);
	}
	
	
	/**
			This is method find by one CostRich
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CostRich> findById(@PathVariable Long id) {
		Optional<CostRich> costRich = costRichRepository.findById(id,2);
		return costRich.isPresent() ? ResponseEntity.ok(costRich.get()) : ResponseEntity.notFound().build();
	}
	
	  
	

	
}
