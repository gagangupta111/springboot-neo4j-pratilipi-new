package com.general.repositories;

import com.general.nodes.Story;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface StoryRepository extends Neo4jRepository<Story, Long> {

    Story findByName(@Param("name") String name);

    Collection<Story> findByNameLike(@Param("name") String name);

    @Query("MATCH (s:Story)<-[r:READ]-(u:User) RETURN s,r,u LIMIT {limit}")
    Collection<Story> graph(@Param("limit") int limit);

}