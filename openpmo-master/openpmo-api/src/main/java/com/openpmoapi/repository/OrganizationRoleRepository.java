package com.openpmoapi.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import com.openpmoapi.model.OrganizationRole;

public interface OrganizationRoleRepository extends Neo4jRepository <OrganizationRole, Long>{

	
	OrganizationRole findByName(@Param("name") String name);
	
	//Collection<OrganizationRole> findByNameLike(@Param("name") String name);
	
	@Query("MATCH (o:OrganizationRole) WHERE  lower(o.name) contains lower($name) RETURN o")
	Collection<OrganizationRole> findByNameLike(@Param("name") String name);
	
}
