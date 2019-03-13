package pojo;

import java.util.Deque;
import java.util.LinkedList;

public class Lane {
    private Deque<CarInschedule> cars = new LinkedList<>();

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private boolean isDone = false;

    public Deque<CarInschedule> getCars() {
        return cars;
    }

    public void setCars(Deque<CarInschedule> cars) {
        this.cars = cars;
    }
}
