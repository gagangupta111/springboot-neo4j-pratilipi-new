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

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void savePercentage(@RequestBody EdgePercentage edgePercentage){
         service.saveEdgePercentage(edgePercentage);
    }

    @RequestMapping(value = "/findEdgePercentage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Optional<EdgePercentage> findEdgePercentage(@RequestBody EdgePercentage edgePercentage){
        return service.findEdgePercentage(edgePercentage);
    }

    @RequestMapping(value = "/graph/{limit}", method = RequestMethod.GET)
    public Collection<EdgePercentage> graph(@PathVariable("limit") Integer limit){
        return service.graph(limit);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public void deleteAll(){
         service.deleteAll();
    }

}
