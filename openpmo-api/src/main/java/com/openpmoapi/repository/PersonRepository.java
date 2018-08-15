package com.openpmoapi.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.openpmoapi.model.Person;

public interface PersonRepository extends Neo4jRepository <Person, Long>{

	
	Person findByName(@Param("name") String name);
	
	Collection<Person> findByNameLike(@Param("name") String name);
	
	
	
}
