package scheduleSystem.impl;

import pojo.*;
import scheduleSystem.ScheduleCross;

import java.util.*;

public class ScheduleCrossImpl implements ScheduleCross {
    //PriorityQueue<RoadInschedule> roadsInCross = new PriorityQueue<>();
    PriorityQueue<CrossInschedule> crosses = new PriorityQueue<>();
    Map<Integer, RoadInschedule> roads;
    /**
     * 调度器的构造函数，初始化crosses和roads,为调度做准备。
     * CrossInschedule放到PriorityQueue croses中
     * @param list
     */
    public ScheduleCrossImpl(List<Cross> list, Map<Integer, RoadInschedule> roads) {
        this.roads = roads;
        for (Cross cross : list) {
            CrossInschedule crossInschedule = new CrossInschedule();
            crossInschedule.setId(cross.getId());   //通过cross来设置id
            PriorityQueue<RoadInschedule> roadsInCross = new PriorityQueue<RoadInschedule>();   //roadsInCross保存此cross的路
            List<Integer> roadIdlist = cross.getRoadIdList();
            for (int i=0; i<4; i++) {
                if (roadIdlist.get(i) > -1)
                    roadsInCross.add(this.roads.get(roadIdlist.get(i)));
            }
            crossInschedule.setRoadsInCross(roadsInCross);
            crosses.add(crossInschedule);
        }
    }

    @Override
    public void scheduleOneCross(CrossInschedule crossInschedule) {
        PriorityQueue<RoadInschedule> roadsInCross = crossInschedule.getRoadsInCross();
        //对于每条路(按优先级选取)
        for (RoadInschedule road : roadsInCross) {
            //选择一辆在排队的车

        }
        //
    }

    /**
     * 选一辆在等待车，无论是否与其他车发生冲突。
     * 并且记录当前车道是否还存在等待的车。
     * @param road
     * @return
     */
    private CarInschedule getCarFromRoad(RoadInschedule road) {
        for (Lane lane : road.getLanes()) {
            Deque<CarInschedule> cars = lane.getCars();
            for (CarInschedule car : cars) {
                //if (car.isWaitflag() && )
            }
        }
        return null;
    }

    @Override
    public void scheduleAllCross() {

    }
}
