package pojo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class CrossInschedule implements Comparable{
    private int id;
    private List<Integer> roadids = new ArrayList<>();
    private PriorityQueue<RoadInschedule> roadsPQ = new PriorityQueue<RoadInschedule>();
    private List<RoadInschedule> roadsList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PriorityQueue<RoadInschedule> getRoadsPQ() {
        return roadsPQ;
    }

    public List<RoadInschedule> getRoadsList() {
        return roadsList;
    }

    public void setRoadsList(List<RoadInschedule> roadsList) {
        this.roadsList = roadsList;
    }

    public void setRoadsPQ(PriorityQueue<RoadInschedule> roadsInCross) {
        this.roadsPQ = roadsInCross;
    }

    public List getRoadids() {
        return roadids;
    }

    public void setRoadids(List roadids) {
        this.roadids = roadids;
    }

    @Override
    public int compareTo(Object o) {
        CrossInschedule cross = (CrossInschedule) o;
        if (this.id < cross.id)
            return -1;
        if (this.id > cross.id)
            return 1;
        return 0;
    }
}
