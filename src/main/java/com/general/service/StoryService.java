package com.general.service;

import com.general.mergecontrol.thread.WorkerThread;
import com.general.nodes.EdgePercentage;
import com.general.nodes.Story;
import com.general.nodes.User;
import com.general.repositories.EdgePercentageRepository;
import com.general.repositories.StoryRepository;
import com.general.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@PropertySource("classpath:application.properties")
public class StoryService {

    private final static Logger LOG = LoggerFactory.getLogger(StoryService.class);

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EdgePercentageRepository edgePercentageRepository;

    private ExecutorService executor;

    @Value("${executor.threads.size}")
    private int threads;

    public StoryService(StoryRepository storyRepository, UserRepository userRepository,
                        EdgePercentageRepository edgePercentageRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.edgePercentageRepository = edgePercentageRepository;
    }

    @PostConstruct
    public void init() {
        executor = Executors.newFixedThreadPool(threads);
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

    @Transactional(readOnly = true)
    public List<EdgePercentage>  findReadRelation(EdgePercentage edgePercentage) {

        return storyRepository.findReadRelation(edgePercentage.getStory().getName(), edgePercentage.getUser().getName());
    }

    @Transactional
    public List<EdgePercentage> mergeEdgePercentage(EdgePercentage edgePercentage){

        return storyRepository.mergeReadRelation(edgePercentage.getStory().getName(), edgePercentage.getUser().getName(), edgePercentage.getReadPercentage());

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<EdgePercentage> updateEdgePercentage(EdgePercentage edgePercentage){

        return storyRepository.updateReadRelation(edgePercentage.getStory().getName(), edgePercentage.getUser().getName(), edgePercentage.getReadPercentage());

    }

    public List<EdgePercentage> findAndUpdateReadRelation(EdgePercentage edgePercentage){

        if (!findReadRelation(edgePercentage).isEmpty()){
            updateEdgePercentage(edgePercentage);
        }else {
            executor.submit(new WorkerThread(edgePercentage));
        }
        return findReadRelation(edgePercentage);

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
    public Collection<EdgePercentage>  graph(Integer limit) {

        Collection<EdgePercentage> result = storyRepository.graph(limit);
        return result;
    }

    @Transactional
    public void deleteAll() {

        storyRepository.deleteAll();
        userRepository.deleteAll();
    }

    // More Getters

    public Set<Story> findUserWithAllStories(String userId){
        return storyRepository.findUserWithAllStories(userId);
    }

    public Optional<User> findUserWithAllReads(String userId){
        return storyRepository.findUserWithAllReads(userId);
    }

    public Set<User> findAllUsersForStory(String sid){
        return storyRepository.findAllUsersForStory(sid);
    }

    public Optional<Story> findStoryWithAllReads(String sid){
        return storyRepository.findStoryWithAllReads(sid);
    }

    public Set<Story> findNotReadStoriesForUser(String userId){
        return storyRepository.findNotReadStoriesForUser(userId);
    }

    public Set<Story> findStoriesForUserWithReadHigherThanPercent(String userId, Integer readPercent){
        return storyRepository.findStoriesForUserWithReadHigherThanPercent(userId, readPercent);
    }

    public Set<Story> findStoriesForUserWithReadLowerThanPercent(String userId, Integer readPercent){
        return storyRepository.findStoriesForUserWithReadLowerThanPercent(userId, readPercent);
    }

    public Set<User> findUsersWithOutStory(){
        return storyRepository.findUsersWithOutStory();
    }


}
