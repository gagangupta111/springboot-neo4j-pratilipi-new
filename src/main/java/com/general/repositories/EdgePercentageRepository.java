package com.general.repositories;

import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EdgePercentageRepository extends Neo4jRepository<EdgePercentage, Long> {
}
