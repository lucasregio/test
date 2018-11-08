package com.openpmoapi.resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import com.openpmoapi.model.WorkpackTemplate;
import com.openpmoapi.model.property.AdressProperty;
import com.openpmoapi.model.property.CostProperty;
import com.openpmoapi.model.property.MeasureProperty;
import com.openpmoapi.model.property.NumberListProperty;
import com.openpmoapi.model.property.NumberProperty;
import com.openpmoapi.model.property.StatusListProperty;
import com.openpmoapi.model.property.StatusProperty;
import com.openpmoapi.model.property.TextListProperty;
import com.openpmoapi.model.property.TextProperty;
import com.openpmoapi.repository.WorkpackTemplateRepository;
import com.openpmoapi.service.WorkpackTemplateService;

/**
* Type here a brief description of the class.
*
* @author marcos.santos  
* @since 2018-08-02
*/
@RestController
@RequestMapping("/api/workpacktemplate")
public class WorkpackTemplateResource {
	
	@Autowired
	private WorkpackTemplateRepository wptmplRepository;
	
	
	@Autowired
	private WorkpackTemplateService wptmpService;
	


	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * This is method delete one WorkpackTemplate
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		wptmplRepository.deleteById(id);
	}
	
	/**
	 * This is method delete all WorkpackTemplate
	 */
	@DeleteMapping("/all")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAll() {
		wptmplRepository.deleteAll();
	}
	
	
	/**
	 * This is method delete part WorkpackTemplate
	 */
	@DeleteMapping("/part")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLista(@PathVariable  Iterable<? extends WorkpackTemplate>  wpTmpl ) {
		wptmplRepository.deleteAll(wpTmpl);
	}
	
	
	/**
	 * This is method update WorkpackTemplate
	 */
	@PutMapping("/{id}")
	public ResponseEntity<WorkpackTemplate> update(@PathVariable  Long id,@Valid  @RequestBody WorkpackTemplate wpTmpl) {
		WorkpackTemplate wpTmplSalvo = wptmpService.update(id, wpTmpl);
		return ResponseEntity.ok(wpTmplSalvo);
	}
	
	
	
	/**
		This is method save WorkpackTemplate
	 */
	@PostMapping
	public ResponseEntity<WorkpackTemplate> save( @Valid @RequestBody WorkpackTemplate wpTmpl, HttpServletResponse response) {
		WorkpackTemplate wpTmplSalvo = wptmplRepository.save(wpTmpl,0);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, wpTmplSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(wptmplRepository.save(wpTmpl));
	}
	
	
//	@PostMapping
//	public String greetingJson(HttpEntity<String> httpEntity) {
//	    String json = httpEntity.getBody();
//	    // json contains the plain json string
//	    return json;
//	}
	
	
	/**
	 * This is method find by all WorkpackTemplates
	 */
	@GetMapping
	public Iterable<WorkpackTemplate> findByAll() {
		 return wptmplRepository.findAll(2);
	}
	
	
	/**
	 * This is method find by all WorkpackTemplates
	 */
	@GetMapping("/listworkpacktemplatesbyid/{id}/{depth")
	public Iterable<WorkpackTemplate> findByAllById(@PathVariable Iterable<Long> id,int depth) {
		 return wptmplRepository.findAllById(id, depth);
	}
	
	
	
	
	/**
			This is method find by one WorkpackTemplate
	 */
	@GetMapping("/{id}/{depth}")
	public ResponseEntity<WorkpackTemplate> findById(@PathVariable Long id,@PathVariable int depth) {
		Optional<WorkpackTemplate> wpTmpl = wptmplRepository.findById(id,depth);
		return wpTmpl.isPresent() ? ResponseEntity.ok(wpTmpl.get()) : ResponseEntity.notFound().build();
	}
	
	
		/**
		This is method find by one WorkpackTemplate
	*/
	@GetMapping("/{id}")
		public ResponseEntity<WorkpackTemplate> findById(@PathVariable Long id) {
		Optional<WorkpackTemplate> wpTmpl = wptmplRepository.findById(id,2);
		return wpTmpl.isPresent() ? ResponseEntity.ok(wpTmpl.get()) : ResponseEntity.notFound().build();
	}
		
		
		
		/**
		This is method find by one or more WorkPack Templates
	*/
	@GetMapping("/listworkpacktemplates/{id}")
	public Collection<WorkpackTemplate> findWptpByIdSchemaTmpl(@PathVariable Long id) {
		return wptmpService.findWptpByIdSchemaTmpl(id);
	}
	

	
		
		/**
		This is method find by one WorkpackTemplate
	*/
	@GetMapping("/tree/{id}")
	public ResponseEntity<WorkpackTemplate> findByIdWptm(@PathVariable Long id) {
		Optional<WorkpackTemplate> wpTmpl = wptmplRepository.findById(id,100);
		return wpTmpl.isPresent() ? ResponseEntity.ok(wpTmpl.get()) : ResponseEntity.notFound().build();
	}

	
	
	
	
//	/**
//	This is method find by all properties of the WorkPack Templates
//*/
//	@GetMapping("/listpropertytypesMap")
//	public Object findAllPropertiesMap() {
//		
//		Map<String,Object> properties = new HashMap<>();
//         
//		properties.put("Text", new TextProperty());
//		properties.put("TextList", new TextListProperty());
//		properties.put("Number", new NumberProperty());
//		properties.put("Address", new AddressProperty());
//		properties.put("Measurement", new MeasureProperty());
//		properties.put("Cost", new CostProperty());
//		properties.put("Status", new StatusProperty());
//		
//     return properties;
//        		
//		
//	}
	
	
	/**
	This is method find by all properties of the WorkPackTemplates
*/
	@GetMapping("/listpropertytypes")
	public Object findAllPropertiesList() {
		
		List<Object> properties = new ArrayList<>();
         
		properties.add(new TextProperty());
		properties.add(new TextListProperty());
		properties.add(new NumberProperty());
		properties.add(new NumberListProperty());
		properties.add(new AdressProperty());
		properties.add(new MeasureProperty());
		properties.add(new CostProperty());
		properties.add(new StatusProperty());
		properties.add(new StatusListProperty());
		
     return properties;
        		
		
	}
	


}
