package com.general.repositories;

import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StoryRepository extends Neo4jRepository<Story, Long> {

    Story findByName(@Param("name") String name);

    Collection<Story> findByNameLike(@Param("name") String name);

    @Query("MATCH (s:Story)<-[r:READ]-(u:User) RETURN s,r,u LIMIT {limit}")
    Collection<EdgePercentage> graph(@Param("limit") int limit);

    @Query("MATCH (u:User {name:{userId}}), (s:Story {name:{sid}}), (u)-[r:READ]->(s) SET r.readPercentage = {readPercent} RETURN s, u, r")
    List<EdgePercentage> updateReadRelation(@Param("sid")String sid, @Param("userId") String userId, @Param("readPercent") Integer readPercent);

    @Query("MATCH (u:User {name:{userId}}), (s:Story {name:{sid}}) MERGE (u)-[r:READ{readPercentage:{readPercent}}]->(s) RETURN s, u, r")
    List<EdgePercentage> mergeReadRelation(@Param("sid")String sid,@Param("userId") String userId, @Param("readPercent") Integer readPercent);

    @Query("MATCH (u:User {name:{userId}}), (s:Story {name:{sid}}), (u)-[r:READ]->(s) RETURN s, u, r")
    List<EdgePercentage> findReadRelation(@Param("sid")String sid,@Param("userId") String userId);



    @Query("MATCH (u:User {name:{userId}}), (s:Story), (u)-[r:READ]->(s) RETURN s, u, r")
    Set<Story> findUserWithAllStories(@Param("userId") String userId);

    @Query("MATCH (u:User {name:{userId}}), (s:Story), (u)-[r:READ]->(s) RETURN s, u, r")
    Optional<User> findUserWithAllReads(@Param("userId") String userId);

    @Query("MATCH (u:User), (s:Story {name:{sid}}), (u)-[r:READ]->(s) RETURN s, u, r")
    Set<User> findAllUsersForStory(@Param("sid") String sid);

    @Query("MATCH (u:User), (s:Story {name:{sid}}), (u)-[r:READ]->(s) RETURN s, u, r")
    Optional<Story> findStoryWithAllReads(@Param("sid") String sid);

    @Query("MATCH (u:User {name:{userId}}), (s:Story) WHERE NOT (u)-[:READ]->(s) RETURN s, u")
    Set<Story> findNotReadStoriesForUser(@Param("userId") String userId);

    @Query("MATCH (u:User {name:{userId}}), (s:Story), (u)-[r:READ]->(s) WHERE r.readPercentage > {readPercent} RETURN s, u, r")
    Set<Story> findStoriesForUserWithReadHigherThanPercent(@Param("userId") String userId, @Param("readPercent") Integer readPercent);

    @Query("MATCH (u:User {name:{userId}}), (s:Story), (u)-[r:READ]->(s) WHERE r.readPercentage < {readPercent} RETURN s, u, r")
    Set<Story> findStoriesForUserWithReadLowerThanPercent(@Param("userId") String userId, @Param("readPercent") Integer readPercent);

    @Query("MATCH (u:User) WHERE NOT (u)-[:READ]->() RETURN u")
    Set<User> findUsersWithOutStory();



}
