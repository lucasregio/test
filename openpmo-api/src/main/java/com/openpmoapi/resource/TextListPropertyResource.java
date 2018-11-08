/**
 * 
 */
package com.openpmoapi.resource;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openpmoapi.repository.TextListPropertyRepository;

/**
* Type here a brief description of the class.
*
* @author marcos.santos  
* @since 2018-08-29
*/
@RestController
@RequestMapping("/api/textlistproperty")
public class TextListPropertyResource {

	
//	@Autowired
//	private WorkpackTemplateRepository wptmplRepository;
	
	
	@Autowired
	private TextListPropertyRepository textListRepository;
	
	@Autowired
	WorkpackTemplateResource wptemplate;
//	
//	@Autowired
//	private WorkpackTemplateService wptmpService;
//	
	
	

	/**
	 * This is method delete one textProperty
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		textListRepository.deleteById(id);
	}
	
	
//
//	/**
//	 * This is method update WorkpackTemplate
//	 */
//	@PutMapping("/{id}")
//	public ResponseEntity<WorkpackTemplate> update(@PathVariable  Long id,@Valid  @RequestBody TextListProperty textList, WorkpackTemplate wpTmpl ) {
//		
//		Optional<WorkpackTemplate> wp = wptmplRepository.findById(id,2);
//		
//		wp.get().getProperties().add(textList);
//		
//		WorkpackTemplate wpSalvo = wptmpService.update(id,wp);
//		
//		return ResponseEntity.ok(wpSalvo);
//	}
//	

	
	
}
