package pojo;

import pojo.CarInschedule;
import pojo.Lane;

import java.util.ArrayList;
import java.util.List;

public class RoadInschedule {
    private List<Lane> lanes = new ArrayList<>();

    public List<Lane> getLanes() {
        return lanes;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }
}
