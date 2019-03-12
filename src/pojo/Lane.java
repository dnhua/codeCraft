package pojo;

import java.util.Deque;
import java.util.LinkedList;

public class Lane {
    private Deque<CarInschedule> cars = new LinkedList<>();

    public Deque<CarInschedule> getCars() {
        return cars;
    }

    public void setCars(Deque<CarInschedule> cars) {
        this.cars = cars;
    }
}
