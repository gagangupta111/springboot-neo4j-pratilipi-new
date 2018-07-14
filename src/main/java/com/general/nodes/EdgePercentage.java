package com.general.nodes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.*;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "readPercentage",
        scope     = Integer.class)
@RelationshipEntity(type = "READ")
public class EdgePercentage {

    @Id
    @GeneratedValue
    private Long id;
    private Integer readPercentage = new Integer(0);

    @StartNode
    private User user;

    @EndNode
    private Story story;

    public EdgePercentage() {
    }

    public EdgePercentage(Integer readPercentage, User user, Story story) {
        this.readPercentage = readPercentage;
        this.user = user;
        this.story = story;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReadPercentage() {
        return readPercentage;
    }

    public void setReadPercentage(Integer readPercentage) {
        this.readPercentage = readPercentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "EdgePercentage{" +
                "id=" + id +
                ", readPercentage=" + readPercentage +
                ", user=" + user +
                ", story=" + story +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgePercentage that = (EdgePercentage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(story, that.story);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, story);
    }
}
