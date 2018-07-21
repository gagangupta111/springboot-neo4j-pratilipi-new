package com.general.mergecontrol.thread;

import com.general.nodes.EdgePercentage;
import com.general.service.StoryService;
import com.general.util.BeanUtil;

public class WorkerThread implements Runnable {

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
        if (!storyService.findReadRelation(edgePercentage).isEmpty()){
            storyService.updateEdgePercentage(edgePercentage);
        }else {
            storyService.mergeEdgePercentage(edgePercentage);
        }

    }
}
