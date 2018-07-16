package com.general.nodes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "story_id",
        scope     = Long.class)
@NodeEntity
public class Story {

    @Id
    @GeneratedValue
    private Long story_id;
    private String name;
    private String isbn;

    @JsonIgnoreProperties("readings")
    @Relationship(type = "READ", direction = Relationship.INCOMING)
    private Set<EdgePercentage> readings  = new HashSet<>();

    private Set<User> users = new HashSet<>();

    public Story() {
    }

    public Story(String name, String isbn) {
        this.name = name;
        this.isbn = isbn;
    }

    public Long getStory_id() {
        return story_id;
    }

    public void setStory_id(Long story_id) {
        this.story_id = story_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Set<EdgePercentage> getReadings() {
        return readings;
    }

    public void setReadings(Set<EdgePercentage> readings) {
        this.readings = readings;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return Objects.equals(story_id, story.story_id) &&
                Objects.equals(name, story.name) &&
                Objects.equals(isbn, story.isbn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(story_id, name, isbn);
    }

    @Override
    public String toString() {
        return "Story{" +
                "story_id=" + story_id +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", readings=" + readings +
                '}';
    }
}
