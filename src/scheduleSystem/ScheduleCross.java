package scheduleSystem;

import pojo.CrossInschedule;
import pojo.RoadInschedule;

public interface ScheduleCross {
    /**
     * 调度一个路口一个时间片
     */
    void scheduleOneCross(CrossInschedule crossInschedule);
    /**
     * 调度所有的路口
     */
    void scheduleAllCross();

    int choiceLane(CrossInschedule cross, RoadInschedule road);
}
