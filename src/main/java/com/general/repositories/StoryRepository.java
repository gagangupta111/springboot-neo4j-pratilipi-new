package com.general.repositories;

import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
import java.util.Optional;

public interface StoryRepository extends Neo4jRepository<Story, Long> {

    Story findByName(@Param("name") String name);

    Collection<Story> findByNameLike(@Param("name") String name);

    @Query("MATCH (s:Story)<-[r:READ]-(u:User) WHERE r.story={story_id} AND r.user={user_id} RETURN s, r, u")
    Optional<EdgePercentage> findEdgePercentage(@Param("story_id") Story story_id, @Param("user_id") User user_id);

    @Query("MATCH (s:Story)<-[r:READ]-(u:User) RETURN s,r,u LIMIT {limit}")
    Collection<EdgePercentage> graph(@Param("limit") int limit);

    @Query("MATCH (u:User {name:{userId}}), (s:Story {name:{sid}}) MERGE (u)-[r:READ{readPercentage:{readPercent}}]->(s) RETURN s, u, r")
    Optional<EdgePercentage> updateReadRelation(@Param("sid")String sid,@Param("userId") String userId, @Param("readPercent") Integer readPercent);
}
