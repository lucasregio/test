package com.openpmoapi.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.openpmoapi.model.Cost;

public interface CostRepository extends Neo4jRepository <Cost, Long>{

	
	
}
