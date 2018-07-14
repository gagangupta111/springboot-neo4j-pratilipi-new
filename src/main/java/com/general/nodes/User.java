package com.general.nodes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import java.util.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "user_id",
        scope     = Long.class)
@NodeEntity
public class User {

    @Id @GeneratedValue
    private Long user_id;
    private String name;

    @Relationship(type = "READ")
    private Set<Story> stories = new HashSet<>();

    private Set<EdgePercentage> readings = new HashSet<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Story> getStories() {
        return stories;
    }

    public void setStories(Set<Story> stories) {
        this.stories = stories;
    }

    public Set<EdgePercentage> getReadings() {
        return readings;
    }

    public void setReadings(Set<EdgePercentage> readings) {
        this.readings = readings;
    }

    public boolean updatePercentage(Story story, int percentage){

        for (EdgePercentage e : this.readings){
            if (e.getStory().equals(story)) {
                e.setReadPercentage(percentage);
                this.stories.add(story);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user_id, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", stories=" + stories +
                '}';
    }
}
