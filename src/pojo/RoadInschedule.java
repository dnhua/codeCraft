package pojo;

import java.util.ArrayList;
import java.util.List;

public class RoadInschedule {
    private List<Lane> lanes = new ArrayList<>();
    private boolean isDone = false;

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
}
