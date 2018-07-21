package com.general.mergecontrol.thread;

import com.general.nodes.EdgePercentage;
import com.general.service.StoryService;
import com.general.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorkerThread implements Runnable {

    private static volatile Map<String, Set<String>> map = new HashMap<>();

    private EdgePercentage edgePercentage;
    private static final StoryService storyService = BeanUtil.getBean(StoryService.class);;

    public WorkerThread() {
    }

    public WorkerThread(EdgePercentage edgePercentage) {
        this.edgePercentage = edgePercentage;
    }

    public EdgePercentage getEdgePercentage() {
        return edgePercentage;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName() );
        Set<String> stories = map.get(edgePercentage.getUser().getName());
        if ( stories == null ){
            storyService.mergeEdgePercentage(edgePercentage);
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add(edgePercentage.getStory().getName());
            map.put(edgePercentage.getUser().getName(), hashSet);
        }else if ( !stories.contains(edgePercentage.getStory().getName()) ){
            map.get(edgePercentage.getUser().getName()).add(edgePercentage.getStory().getName());
            storyService.mergeEdgePercentage(edgePercentage);
        }else {
            storyService.updateEdgePercentage(edgePercentage);
        }

    }
}
