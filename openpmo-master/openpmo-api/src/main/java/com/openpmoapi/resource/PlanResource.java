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
import com.openpmoapi.model.Plan;
import com.openpmoapi.repository.PlanRepository;
import com.openpmoapi.service.PlanService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/plan")
@Api(value = "/api/plan",  tags = "Plan",description=" ")
public class PlanResource {
	
	@Autowired
	private PlanRepository planRepository;
	
	
	@Autowired
	private PlanService planService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * This method delete one Plan
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		planRepository.deleteById(id);
	}
	
	
	
	/**
	 * This method update Plan
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Plan> update(@PathVariable Long id, @Valid @RequestBody Plan plan) {
		Plan planSalvo = planService.update(id, plan);
		return ResponseEntity.ok(planSalvo);
	}
	
	
	/**
		This method save Plan
	 */
	@PostMapping
	public ResponseEntity<Plan> save(@Valid @RequestBody Plan plan, HttpServletResponse response) {
		Plan planSalvo = planRepository.save(plan);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, planSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(planRepository.save(plan));
	}
	
	
	/**
	 * This method find by all plan
	 */
	@GetMapping
	public Iterable<Plan> findByAll() {
		 return planRepository.findAll(2);
	}
	
	
	/**
			This method find by one Plan
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Plan> findById(@PathVariable Long id) {
		Optional<Plan> plan = planRepository.findById(id,2);
		return plan.isPresent() ? ResponseEntity.ok(plan.get()) : ResponseEntity.notFound().build();
	}
	
	
		/**
		This method find by one Plan
	*/
	@GetMapping("/listschemas/{id}")
	public Collection<Plan> findPlans(@PathVariable Long id) {
		return planService.findPlanByIdEnveronment(id);
	}
	
	

	/**
		This method find by one Plan tree
	*/
	@GetMapping("/tree/{id}")
	public Collection<Plan> findPlansTree(@PathVariable Long id) {
		return planService.findPlanByIdTree(id);
	}
	
	
}
