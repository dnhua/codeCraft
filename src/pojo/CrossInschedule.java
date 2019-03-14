package pojo;

import java.util.PriorityQueue;

public class CrossInschedule {
    private int id;
    PriorityQueue<RoadInschedule> roadsInCross = new PriorityQueue<RoadInschedule>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PriorityQueue<RoadInschedule> getRoadsInCross() {
        return roadsInCross;
    }

    public void setRoadsInCross(PriorityQueue<RoadInschedule> roadsInCross) {
        this.roadsInCross = roadsInCross;
    }
}
