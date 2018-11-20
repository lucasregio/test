package com.openpmoapi.repository;

import java.util.Collection;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import com.openpmoapi.model.OfficeRole;

public interface OfficeRoleRepository extends Neo4jRepository <OfficeRole, Long>{

	
	OfficeRole findByName(@Param("name") String name);
	
	//Collection<EnvironmentRole> findByNameLike(@Param("name") String name);
	
	@Query("MATCH (e:EnvironmentRole) WHERE  lower(e.name) contains lower($name) RETURN e")
	Collection<OfficeRole> findByNameLike(@Param("name") String name);
	
}
