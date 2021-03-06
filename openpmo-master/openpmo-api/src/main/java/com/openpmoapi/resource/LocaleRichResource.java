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
import com.openpmoapi.model.LocaleRich;
import com.openpmoapi.repository.LocaleItemRepository;
import com.openpmoapi.service.LocaleItemService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/localeItem")
@Api(value = "/api/localeItem",  tags = "Locale",description=" ")
public class LocaleRichResource {
	
	@Autowired
	private LocaleItemRepository localeItemRepository;
	
	
	@Autowired
	private LocaleItemService localeItemService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * This is method delete one LocaleItem
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		localeItemRepository.deleteById(id);
	}
	
	
	/**
	 * This is method update LocaleItem
	 */
	@PutMapping("/{id}")
	public ResponseEntity<LocaleRich> update(@PathVariable Long id, @Valid @RequestBody LocaleRich localeItem) {
		LocaleRich localeItemSalvo = localeItemService.update(id, localeItem);
		return ResponseEntity.ok(localeItemSalvo);
	}
	
	
	/**
		This is method save LocaleItem
	 */
	@PostMapping
	public ResponseEntity<LocaleRich> save(@Valid @RequestBody LocaleRich localeItem, HttpServletResponse response) {
		LocaleRich localeItemSalvo = localeItemRepository.save(localeItem);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, localeItemSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(localeItemRepository.save(localeItem));
	}
	
	
	/**
	 * This is method find by all LocaleItens
	 */
	@GetMapping
	public Iterable<LocaleRich> findByAll() {
		 return localeItemRepository.findAll(2);
	}
	
	
	/**
			This is method find by one LocaleItens
	 */
	@GetMapping("/{id}")
	public ResponseEntity<LocaleRich> findById(@PathVariable Long id) {
		Optional<LocaleRich> localeItem = localeItemRepository.findById(id,2);
		return localeItem.isPresent() ? ResponseEntity.ok(localeItem.get()) : ResponseEntity.notFound().build();
	}
	
	  
	
	/**
	 * This is method find by name LocaleItem
	 * 
	 */
	@GetMapping("/{name}")
	public Collection<LocaleRich> findByNameLike(@PathVariable String name) {
	     return localeItemService.findByNameLike(name);
	 }
	
	

	
}
