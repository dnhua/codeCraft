package scheduleSystem;

import pojo.Lane;
import pojo.RoadInschedule;
import java.util.List;

public interface ScheduleRoad {
    void updateOne(Lane lane);
    void updateOneRoad(RoadInschedule road);
    void updateAll();
    void updateAll(List<RoadInschedule> roads);
}
