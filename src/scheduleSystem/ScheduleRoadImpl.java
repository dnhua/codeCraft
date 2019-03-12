package scheduleSystem;

import pojo.CarInschedule;
import pojo.Lane;
import pojo.RoadInschedule;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class ScheduleRoadImpl implements ScheduleRoad {
    List<RoadInschedule> roads;

    @Override
    public void updateOne(Lane lane) {
        Deque<CarInschedule> cars = lane.getCars();
        Iterator<CarInschedule> it = cars.iterator();
        //对于第一辆车
        CarInschedule car = it.next();

        while (it.hasNext()) {

        }
    }

    private void setCarByInfo(CarInschedule car, CarInschedule carLast) {
        //判断是否是第一辆车(最靠近下一个路口的车)
        if (carLast==null) {
            //如果此时间片该车到达出路口位置
            if (car.getLocation()+car.getRealspeed()>=car.getDistance()) {
                car.setWaitflag(true);
            } else {    //否则更新location
                car.setLocation(car.getLocation()+car.getRealspeed());
            }
        } else {

        }
    }

    @Override
    public void updateOneRoad(RoadInschedule road) {

    }

    @Override
    public void updateAll() {

    }

    @Override
    public void updateAll(List<RoadInschedule> roads) {

    }
}
