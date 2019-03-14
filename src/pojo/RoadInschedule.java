package pojo;

import java.util.ArrayList;
import java.util.List;

public class RoadInschedule implements Comparable{
    private int id;
    private List<Lane> lanes = new ArrayList<>();
    private boolean isDone = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    @Override
    public int compareTo(Object o) {
        RoadInschedule object = (RoadInschedule) o;
        if (this.id < object.id)
            return -1;
        else if (this.id > object.id)
            return 1;
        else
            return 0;
    }
}
