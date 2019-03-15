package pojo;

import java.util.PriorityQueue;

public class CrossInschedule implements Comparable{
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
