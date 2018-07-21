package com.general.controller;

import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import com.general.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/general")
public class ControlllerNeo4j {

    @Autowired
    private StoryService service;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> getAllUsers(){
        return service.getAllUsers();
    }

    @RequestMapping(value = "/stories",method = RequestMethod.GET)
    public Iterable<Story> getAllStories(){
        return service.getAllStories();
    }

    @RequestMapping(value = "/reads", method = RequestMethod.GET)
    public Iterable<EdgePercentage> getAllRelationships(){
        return service.getAllReads();
    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.GET)
    public Story getStoryById(@PathVariable("id") Long id){
        Optional<Story> story = service.getStoryById(id);
        if (story.isPresent()){
            return story.get();
        }else return null;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Long id){
        Optional<User> optional = service.getUserById(id);
        if (optional.isPresent()){
            return optional.get();
        }else return null;
    }

    @RequestMapping(value = "/story", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Story createStory(@RequestBody Story story){
        return service.createStory(story);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user){
        return service.createUser(user);
    }

    @RequestMapping(value = "/updateRead", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EdgePercentage> updateReadRelation(@RequestBody EdgePercentage edgePercentage){
        List<EdgePercentage> percentage = service.findAndUpdateReadRelation(edgePercentage);
        return percentage;
    }

    @RequestMapping(value = "/mergeRead", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EdgePercentage> mergeReadRelation(@RequestBody EdgePercentage edgePercentage){
        List<EdgePercentage> percentage = service.mergeEdgePercentage(edgePercentage);
        return percentage;
    }

    @RequestMapping(value = "/findRead", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EdgePercentage> findReadRelation(@RequestBody EdgePercentage edgePercentage){
        return service.findReadRelation(edgePercentage);
    }

    @RequestMapping(value = "/graph/{limit}", method = RequestMethod.GET)
    public Collection<EdgePercentage> graph(@PathVariable("limit") Integer limit){
        return service.graph(limit);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void deleteAll(){
         service.deleteAll();
    }

    // More Creative Getters

    @RequestMapping(value = "/findUserWithAllStories/{name}", method = RequestMethod.GET)
    Set<Story> findUserWithAllStories(@PathVariable("name") String name){
        return service.findUserWithAllStories(name);
    }

    @RequestMapping(value = "/findUserWithAllReads/{name}", method = RequestMethod.GET)
    Optional<User> findUserWithAllReads(@PathVariable("name") String name){
        return service.findUserWithAllReads(name);
    }

    @RequestMapping(value = "/findAllUsersForStory/{name}", method = RequestMethod.GET)
    Set<User> findAllUsersForStory(@PathVariable("name") String name){
        return service.findAllUsersForStory(name);
    }

    @RequestMapping(value = "/findStoryWithAllReads/{name}", method = RequestMethod.GET)
    Optional<Story> findStoryWithAllReads(@PathVariable("name") String name){
        return service.findStoryWithAllReads(name);
    }

    @RequestMapping(value = "/findNotReadStoriesForUser/{name}", method = RequestMethod.GET)
    Set<Story> findNotReadStoriesForUser(@PathVariable("name") String name){
        return service.findNotReadStoriesForUser(name);
    }

    @RequestMapping(value = "/findMoreReadStoriesForUser/{name}/{percent}", method = RequestMethod.GET)
    Set<Story> findStoriesForUserWithReadHigherThanPercent(@PathVariable("name") String name, @PathVariable("percent") Integer percent){
        return service.findStoriesForUserWithReadHigherThanPercent(name, percent);
    }

    @RequestMapping(value = "/findLessReadStoriesForUser/{name}/{percent}", method = RequestMethod.GET)
    Set<Story> findStoriesForUserWithReadLowerThanPercent(@PathVariable("name") String name, @PathVariable("percent") Integer percent){
        return service.findStoriesForUserWithReadLowerThanPercent(name, percent);
    }

    @RequestMapping(value = "/findUsersWithOutStory", method = RequestMethod.GET)
    Set<User> findUsersWithOutStory(){
        return service.findUsersWithOutStory();
    }


}
