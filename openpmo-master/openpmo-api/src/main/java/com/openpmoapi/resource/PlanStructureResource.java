package com.openpmoapi.resource;

import java.util.Collection;
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
import com.openpmoapi.model.PlanStructure;
import com.openpmoapi.repository.PlanStructureRepository;
import com.openpmoapi.service.PlanStructureService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/planmodel")
@Api(value = "/api/planmodel",  tags = "PlanModel",description=" ")
public class PlanStructureResource {
	
	@Autowired
	private PlanStructureRepository planStructureRepository;
	
	
	@Autowired
	private PlanStructureService planStructureService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * This method delete one PlanStructure
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		planStructureRepository.deleteById(id);
	}
	
	
	
	/**
	 * This method update PlanStructure
	 */
	@PutMapping("/{id}")
	public ResponseEntity<PlanStructure> update(@PathVariable Long id, @Valid @RequestBody PlanStructure planStructure) {
		PlanStructure planStructureSalvo = planStructureService.update(id, planStructure);
		return ResponseEntity.ok(planStructureSalvo);
	}
	
	
	/**
		This method save PlanStructure
	 */
	@PostMapping
	public ResponseEntity<PlanStructure> save(@Valid @RequestBody PlanStructure planStructure, HttpServletResponse response) {
		PlanStructure planStructureSalvo = planStructureRepository.save(planStructure);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, planStructureSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(planStructureRepository.save(planStructure));
	}
	
	
	/**
	 * This method find by all PlanStructure
	 */
	@GetMapping
	public Iterable<PlanStructure> findByAll() {
		 return planStructureRepository.findAll(2);
	}
	
	
	/**
			This method find by one PlanStructure
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PlanStructure> findById(@PathVariable Long id) {
		Optional<PlanStructure> planStructure = planStructureRepository.findById(id,2);
		return planStructure.isPresent() ? ResponseEntity.ok(planStructure.get()) : ResponseEntity.notFound().build();
	}
	
	
		/**
		This method find by one PlanStructure
	*/
	@GetMapping("/listschematemplates/{id}")
	public Collection<PlanStructure> findSchemaTemplates(@PathVariable Long id) {
		return planStructureService.findPlanStructureByIdEnveronment(id);
	}
	
	/**
		This method find by one PlanStructure tree
	*/
	@GetMapping("/tree/{id}")
	public Collection<PlanStructure> findSchemaTmplTree(@PathVariable Long id) {
		return planStructureService.findPlanStructureByIdTree(id);
	}
	
}
