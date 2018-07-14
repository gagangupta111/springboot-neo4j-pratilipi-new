package com.general.service;

import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import com.general.repositories.EdgePercentageRepository;
import com.general.repositories.StoryRepository;
import com.general.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class StoryService {

    private final static Logger LOG = LoggerFactory.getLogger(StoryService.class);

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EdgePercentageRepository edgePercentageRepository;

    public StoryService(StoryRepository storyRepository, UserRepository userRepository, EdgePercentageRepository edgePercentageRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.edgePercentageRepository = edgePercentageRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Iterable<Story> getAllStories(){
        return storyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Story> getStoryById(Long id){
        return storyRepository.findById(id);
    }

    @Transactional
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public Story createStory(Story story){
        return storyRepository.save(story);
    }

    @Transactional
    public User createPercentage(EdgePercentage edgePercentage){
        User user = edgePercentage.getUser();
        user = userRepository.findById(user.getUser_id()).get();
        Story story = edgePercentage.getStory();
        story = storyRepository.findById(story.getStory_id()).get();

        boolean isFound = user.updatePercentage(story, edgePercentage.getReadPercentage());
        if (!isFound){
            EdgePercentage e = new EdgePercentage(edgePercentage.getReadPercentage(), user, story);
            e = edgePercentageRepository.save(e);
            user.getReadings().add(e);
            user.getStories().add(story);
        }
        user = userRepository.save(user);
        return user;
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

    @Transactional(readOnly = true)
    public Story findByName(String name) {
        Story result = storyRepository.findByName(name);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Story> findByNameLike(String name) {
        Collection<Story> result = storyRepository.findByNameLike(name);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Story>  graph(int limit) {
        Collection<Story> result = storyRepository.graph(limit);
        return result;
    }

}
