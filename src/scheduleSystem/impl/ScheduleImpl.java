package scheduleSystem.impl;

import pojo.*;
import scheduleSystem.Schedule;
import scheduleSystem.ScheduleCross;
import scheduleSystem.ScheduleRoad;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleImpl implements Schedule {
    private ScheduleRoad scheduleRoad;
    private ScheduleCross scheduleCross;
    private Answer answer;
    private Map<Integer, RoadInschedule> roads = new HashMap<>();
    private Map<Integer, CrossInschedule> crosses = new HashMap<>();
    private int N = 0;  //时间片

    public ScheduleImpl(Answer answer) {
        scheduleRoad = new ScheduleRoadImpl(roads);
        scheduleCross = new ScheduleCrossImpl(crosses, roads);
        this.answer = answer;
    }

    @Override
    public void choiceNewCarOnTORoad() {
        //对于list里面的每一辆车，判断是否可以上路
        for (int i=0; i<answer.getCarid().size(); i++) {
            List<Integer> pathlist = answer.getPathList().get(i);
            if (pathlist.get(1) < N)
                continue;
            if (choiceLane(pathlist.get(0), pathlist.get(2)) != -1) {
                int laneid = choiceLane(pathlist.get(0), pathlist.get(2));
                CarInschedule car = new CarInschedule();
                //1.计算在下一个车道可以行进的距离
                int s2 = calcNextRoadMaxDistance(pathlist.get(2), car);
                //2.更新car，更新road
                RoadInschedule road = roads.get(pathlist.get(2));
                car.setRoadid(pathlist.get(2));
                car.setLocation(s2);
                car.setRoadspeedlimit(road.getSpeedLimit());
                car.setRealspeed(Math.min(car.getRoadspeedlimit(), car.getRoadspeedlimit()));
                car.setLaneid(laneid);
                car.setWaitflag(false);
                car.setStopflag(true);
                car.setDistance(road.getLength());
                car.setCanOutCross(false);
                int nextcrossid = pathlist.get(0) == road.getBeginId() ? road.getEndId() : road.getBeginId();
                car.setFromTo(pathlist.get(0)+""+nextcrossid);
                car.setNextroadid(pathlist.get(3));  //下条路的id
                car.setDirection(getDirection(pathlist.get(0), pathlist.get(3), pathlist.get(4)));   //转向
                road.addLast(car);
                //3. 最后需要更新roads
                roads.put(road.getId(), road);
            }
        }
    }

    public int getDirection(int id, int roadid, int nextroadid) {
        int nextcrossid;
        if (id == roads.get(roadid).getBeginId())
            nextcrossid = roads.get(roadid).getEndId();
        else
            nextcrossid = roads.get(roadid).getBeginId();
        CrossInschedule cross = crosses.get(nextcrossid);
        List<Integer> roadIds = cross.getRoadIds();
        int id1 = roadIds.indexOf(roadid);
        int id2 = roadIds.indexOf(nextroadid);
        if (id1 == 0) {
            if (id2 == 0) {
                return -1;
            } else if (id2 == 1) {
                return 2;
            } else if (id2 == 2) {
                return 3;
            } else if (id2 == 3) {
                return 1;
            }
        } else if (id1 == 1) {
            if (id2 == 0) {
                return 1;
            } else if (id2 == 1) {
                return -1;
            } else if (id2 == 2) {
                return 2;
            } else if (id2 == 3) {
                return 3;
            }
        } else if (id1 == 2) {
            if (id2 == 0) {
                return 3;
            } else if (id2 == 1) {
                return 1;
            } else if (id2 == 2) {
                return -1;
            } else if (id2 == 3) {
                return 2;
            }
        } else if (id1 == 3) {
            if (id2 == 0) {
                return 2;
            } else if (id2 == 1) {
                return 3;
            } else if (id2 == 2) {
                return 1;
            } else if (id2 == 3) {
                return -1;
            }
        }
        return -1;
    }

    public int calcNextRoadMaxDistance(int roadid, CarInschedule car) {
        //通过car得到下一条路
        int nextroadid = roadid;
        RoadInschedule nextroadInschedule = roads.get(nextroadid);
        int nextspeedLimit = nextroadInschedule.getSpeedLimit();
        int v2 = Math.min(car.getSpeedlimit(), nextspeedLimit);
        int s1 = car.getDistance() - car.getLocation();
        int s2 = v2 > s1 ? v2 - s1 : 0;
        return s2;
    }

    public List<Lane> getLanes(int beginid, int roadid) {
        RoadInschedule road = roads.get(roadid);
        int beginId = road.getBeginId();
        int endId = road.getEndId();
        String fromTo = beginid != beginId ? endId + "->" + beginId : beginId + "->" + endId;
        Map<String, List<Lane>> lanemap = road.getLanemap();
        List<Lane>  lanes = lanemap.get(fromTo);
        return lanes;
    }

    public int choiceLane(int beginid, int roadid) {
        List<Lane> lanes = getLanes(beginid, roadid);
        int i = 0;
        for (; i<lanes.size(); i++) {
            Deque<CarInschedule> cars = lanes.get(i).getCars();
            CarInschedule last = cars.getLast();
            if (last.getLocation() > 0)
                return i;
        }
        return -1;
    }

    @Override
    public void scheduleCarsOnRoads() {
        scheduleRoad.updateAll();
    }

    @Override
    public void scheduleCarsInCross() {
        scheduleCross.scheduleAllCross();
    }

    @Override
    public void scheduleOneTimeSlice() {
        while (answer.getCarid().size() != 0) {
            choiceNewCarOnTORoad();
            scheduleCarsOnRoads();
            scheduleCarsInCross();
        }
    }
}
