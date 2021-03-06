/**
 * 
 */
package com.openpmoapi.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openpmoapi.model.Property;
import com.openpmoapi.repository.PropertyRepository;


/**
* this class is responsible by valid the data 
*
* @author marcos.santos  
* @since 2018-08-02
*/
@Service
public class PropertyService {


	@Autowired
	private PropertyRepository propertyRepository;
		

	
	@Transactional(readOnly = true)
    public Collection<Property> findPropertyByIdPropertyProfile(Long id) {
      Collection<Property> properties = propertyRepository.findPropertyByIdPropertyProfile(id);
      return properties;
    }
	
	
	@Transactional(readOnly = true)
    public Optional<Property> findPropertyByIdProperty(Long id) {
		Optional<Property> property = propertyRepository.findPropertyByIdProperty(id);
      return property;
    }
	
	
	
	
}
