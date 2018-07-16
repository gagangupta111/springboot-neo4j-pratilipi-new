package com.general.repositories;

import com.general.nodes.Story;
import com.general.nodes.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import java.util.Collection;

public interface UserRepository extends Neo4jRepository<User, Long> {

    User findByName(@Param("name") String name);

    Collection<User> findByNameLike(@Param("name") String name);

}
