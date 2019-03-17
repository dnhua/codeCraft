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
            //设置每个cross的idlist(道路的id集合)
            List idlist = getRoadIdList(cross);
            crossInschedule.setRoadids(idlist);
            //设置每个cross的道路list
            //设置每个cross的道路优先队列
            PriorityQueue<RoadInschedule> roadsPQ = new PriorityQueue<RoadInschedule>();   //roadsPQ保存此cross的路
            List roadList = new ArrayList();    //roadList保存此cross的路
            List<Integer> roadIdlist = cross.getRoadIdList();
            for (int i=0; i<4; i++) {
                if (roadIdlist.get(i) > -1){
                    roadsPQ.add(this.roads.get(roadIdlist.get(i)));
                } else if (roadIdlist.get(i) == -1) {
                    roadList.add(null);    //如果没有这条路则为null
                }
            }
            crossInschedule.setRoadsPQ(roadsPQ);
            crossInschedule.setRoadsList(roadList);
            crosses.add(crossInschedule);
        }
    }

    @Override
    public void scheduleOneCross(CrossInschedule crossInschedule) {
        PriorityQueue<RoadInschedule> roadsPQ = crossInschedule.getRoadsPQ();
        //对于每条路(按优先级选取)
        for (RoadInschedule road : roadsPQ) {
            //选择一辆在排队的车
            CarInschedule car = getCarFromRoad(road);
            //如果car为空，或者冲突了，跳过这条路
            if (car == null || isConflicted(crossInschedule, road, car))
                continue;   //这里有bug隐患，四条路都为空/冲突如何跳出循环

        }

    }

    /**
     *
     * @param cross 当前的交叉路口
     * @param road  当前选取车辆的路口
     * @param car   当前选取的车
     * @return      返回是否冲突，boolean
     */
    private boolean isConflicted(CrossInschedule cross, RoadInschedule road, CarInschedule car) {
        List roadids = cross.getRoadids();
        //直行
        if (car.getDirection() == 3)
            return false;
        int i = roadids.indexOf(road.getId());
        //左转
        if (car.getDirection() == 2){
            //直行冲突检查
            if (i == 0) {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(i + 3));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
                else
                    return true;
            } else {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(i - 1));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
                else
                    return true;
            }
        } else {    //右转
            //直行冲突检查
            if (i == 4) {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(0));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
            } else {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(i + 1));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
            }
            //左行冲突检查
            if (i == 0 || i == 1) {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(i + 2));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
                else
                    return true;
            } else if (i == 2) {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(0));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
                else
                    return true;
            } else if (i==3) {
                CarInschedule car1 = getCarFromRoad(cross.getRoadsList().get(1));
                if (car1 != null || car1.getDirection() == 3)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }

    /**
     * 选一辆在等待车，无论是否与其他车发生冲突。
     * 如果前road没有满足条件的等待车辆则返回null。
     * @param road
     * @return
     */
    private CarInschedule getCarFromRoad(RoadInschedule road) {
        for (Lane lane : road.getLanes()) {
            Deque<CarInschedule> cars = lane.getCars();
            for (CarInschedule car : cars) {
                if (car.isCanOutCross())
                    return car;
            }
        }
        return null;
    }

    private List getRoadIdList(Cross cross) {
        List idlist = new ArrayList();
        idlist.add(cross.getRoadId1());
        idlist.add(cross.getRoadId2());
        idlist.add(cross.getRoadId3());
        idlist.add(cross.getRoadId4());
        return idlist;
    }

    @Override
    public void scheduleAllCross() {

    }
}
