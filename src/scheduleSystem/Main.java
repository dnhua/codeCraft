package scheduleSystem;

import org.junit.jupiter.api.Test;
import pojo.*;
import scheduleSystem.impl.ScheduleImpl;
import utils.ReadData;

import java.util.*;

public class Main {

    @Test
    public void test1() {
        //1.读取answer.txt文件
        Answer answer = ReadData.readAnswer("data/answer.txt", "data/car.txt");
        System.out.println(answer.getPathList());
        //2.读取roads，cars
        List<Car> carlist = ReadData.readCar("data/car.txt");
        List<Cross> crosslist = ReadData.readCross("data/cross.txt");
        List<Road> roadlist = ReadData.readRoad("data/road.txt");
        //2.准备roads，crosses
        Map<Integer, CrossInschedule> crosses = initCrossesMap(crosslist);
        Map<Integer, RoadInschedule> roads = initRoadsMap(roadlist);

        ScheduleImpl schedule = new ScheduleImpl(answer, roads, crosses);
        schedule.scheduleOneTimeSlice();
    }

    public Map<Integer, RoadInschedule> initRoadsMap(List<Road> roadlist) {
        Map<Integer, RoadInschedule> roads = new HashMap<>();
        for (int i=0; i<roadlist.size(); i++) {
            RoadInschedule road = new RoadInschedule();
            road.setBeginId(roadlist.get(i).getBeginId());
            road.setEndId(roadlist.get(i).getEndId());
            road.setBidirectional(roadlist.get(i).isBidirectional());
            road.setDone(false);
            road.setId(roadlist.get(i).getId());
            road.setLength(roadlist.get(i).getLength());
            road.setSpeedLimit(roadlist.get(i).getSpeedLimit());
            Map<String, List<Lane>> lanemap = new HashMap<>();
            List<Lane> lane1 = new ArrayList<>();
            for (int j=0; j<roadlist.get(i).getLaneNums(); j++)
                lane1.add(new Lane());
            lanemap.put(road.getBeginId()+"->"+road.getEndId(), lane1);
            if (road.isBidirectional()) {
                List<Lane> lane2 = new ArrayList<>();
                for (int j=0; j<roadlist.get(i).getLaneNums(); j++)
                    lane1.add(new Lane());
                lanemap.put(road.getEndId()+"->"+road.getBeginId(), lane2);
            }
            road.setLanemap(lanemap);
            roads.put(road.getId(), road);
        }
        return roads;
    }

    public Map<Integer, CrossInschedule> initCrossesMap(List<Cross> crosslist) {
        Map<Integer, CrossInschedule> crosses = new HashMap<>();
        for (int i=0; i<crosslist.size(); i++) {
            CrossInschedule crossInschedule = new CrossInschedule();
            crossInschedule.setId(crosslist.get(i).getId());
            List<Integer> roadids = new ArrayList<>();
            roadids.add(crosslist.get(i).getRoadId1());
            roadids.add(crosslist.get(i).getRoadId2());
            roadids.add(crosslist.get(i).getRoadId3());
            roadids.add(crosslist.get(i).getRoadId4());
            List<Integer> roadPQ = new ArrayList<>(roadids);
            Collections.sort(roadPQ);
            List<Integer> roadIndexPQ = new ArrayList<>();
            roadIndexPQ.add(roadids.indexOf(roadPQ.get(0)));
            roadIndexPQ.add(roadids.indexOf(roadPQ.get(1)));
            roadIndexPQ.add(roadids.indexOf(roadPQ.get(2)));
            roadIndexPQ.add(roadids.indexOf(roadPQ.get(3)));
            crossInschedule.setRoadIds(roadids);
            crossInschedule.setRoadIndexPQ(roadIndexPQ);
            crosses.put(crossInschedule.getId(), crossInschedule);
        }
        return crosses;
    }
}
