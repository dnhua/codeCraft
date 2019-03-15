package scheduleSystem;

import pojo.CarInschedule;
import pojo.Lane;
import pojo.RoadInschedule;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class ScheduleRoadImpl implements ScheduleRoad {
    Map<Integer, RoadInschedule> roads = new HashMap<>();

    public ScheduleRoadImpl(List<RoadInschedule> roads) {
        //for
    }

    public ScheduleRoadImpl() {

    }

    @Override
    public void updateOne(Lane lane) {
        Deque<CarInschedule> cars = lane.getCars();
        Iterator<CarInschedule> it = cars.iterator();
        CarInschedule car;
        CarInschedule carLast = null;
        //对于第一辆车
        if (it.hasNext()) {
            car = it.next();
            setCarByInfo(car, null);
            carLast = car;
        }

        while (it.hasNext()) {
            car = it.next();
            setCarByInfo(car, carLast);
            carLast = car;
        }
    }

    private void setCarByInfo(CarInschedule car, CarInschedule carLast) {
        //判断是否是第一辆车(最靠近下一个路口的车)
        if (carLast==null) {
            //如果此时间片该车到达出路口位置
            if (car.getLocation() + car.getRealspeed() >= car.getDistance()) {
                car.setWaitflag(true);
                car.setLocation(car.getDistance());
                car.setCanOutCross(true);
            } else {    //否则更新location
                car.setLocation(car.getLocation()+car.getRealspeed());
                car.setStopflag(true);
                if (car.getLocation() + car.getRealspeed() > car.getDistance())
                    car.setCanOutCross(true);
            }
        } else {
            int s = carLast.getLocation() - car.getLocation();
            //如果前面一辆车处于等待状态
            if (carLast.isWaitflag()) {
                car.setWaitflag(true);  //更新状态
                //如果当前车速*时间大于s
                if (s < car.getRealspeed())
                    car.setLocation(carLast.getLocation());
                else
                    car.setLocation(car.getLocation() + car.getRealspeed());
            } else if (carLast.isStopflag()) {
                car.setStopflag(true);
                car.setRealspeed(Math.min(car.getRealspeed(), s));
                car.setLocation(car.getLocation()+car.getRealspeed());
            } else {
                car.setStopflag(true);
                car.setLocation(car.getLocation()+car.getRealspeed());
            }
        }
    }

    @Override
    public void updateOneRoad(RoadInschedule road) {
        List<Lane> lanes = road.getLanes();
        for (Lane lane : lanes) {
            updateOne(lane);
        }
    }

    @Override
    public void updateAll() {
        for (Map.Entry<Integer, RoadInschedule> entry : roads.entrySet()) {
            updateOneRoad(entry.getValue());
        }
    }

    @Override
    public void updateAll(List<RoadInschedule> roads) {
//        for (RoadInschedule road : roads) {
//            updateOneRoad(road);
//        }
    }

    public Map<Integer, RoadInschedule> getRoads() {
        return roads;
    }

    public void setRoads(Map<Integer, RoadInschedule> roads) {
        this.roads = roads;
    }
}
