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

    public StoryService(StoryRepository storyRepository, UserRepository userRepository,
                        EdgePercentageRepository edgePercentageRepository) {
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
    public Iterable<EdgePercentage> getAllReads(){
        return edgePercentageRepository.findAll();
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
    public Optional<EdgePercentage> saveEdgePercentage(EdgePercentage edgePercentage){

        return storyRepository.updateReadRelation(edgePercentage.getStory().getName(), edgePercentage.getUser().getName(), edgePercentage.getReadPercentage());

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
    public Optional<EdgePercentage>  findEdgePercentage(EdgePercentage edgePercentage) {

        User user = edgePercentage.getUser();
        user = userRepository.findById(user.getUser_id()).get();
        Story story = edgePercentage.getStory();
        story = storyRepository.findById(story.getStory_id()).get();

        Optional<EdgePercentage> result = storyRepository.findEdgePercentage(story, user);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<EdgePercentage>  graph(Integer limit) {

        Collection<EdgePercentage> result = storyRepository.graph(limit);
        return result;
    }

    @Transactional
    public void deleteAll() {

        storyRepository.deleteAll();
        userRepository.deleteAll();
    }

}
