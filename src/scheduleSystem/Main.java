package scheduleSystem;

import org.junit.jupiter.api.Test;
import pojo.*;
import scheduleSystem.impl.ScheduleImpl;
import utils.FileUtil;
import utils.ReadData;

import java.util.*;

public class Main {
    static public Answer answersubmit = ReadData.readAnswer("data/exam/answer.txt", "data/exam/car.txt");
    @Test
    public void test1() {
        //1.读取answer.txt文件
        Answer answer1 = ReadData.readAnswer("data/exam/answer.txt", "data/exam/car.txt");
//        System.out.println(answer1.getPathList().get(0));
//        System.out.println(answer.getPathList());
        //2.读取roads，cars
        List<Car> carlist = ReadData.readCar("data/exam/car.txt");
        List<Cross> crosslist = ReadData.readCross("data/exam/cross.txt");
        List<Road> roadlist = ReadData.readRoad("data/exam/road.txt");
        Answer answer = makeAnswerInorder(answer1, carlist);
//        Answer answer = answer1;
        System.out.println("answer 准备完毕！");
//        System.out.println(answer.getPathList());
        //2.准备roads，crosses
        Map<Integer, CrossInschedule> crosses = initCrossesMap(crosslist);
        Map<Integer, RoadInschedule> roads = initRoadsMap(roadlist);

        ScheduleImpl schedule = new ScheduleImpl(answer, roads, crosses, carlist);
        schedule.schedule();
        System.out.println(ScheduleImpl.answer.getCarid().size());
        System.out.println(ScheduleImpl.N);
        FileUtil.ListAnswer2txt(Main.answersubmit,"data/answer/answer.txt");
    }

    public Answer makeAnswerInorder(Answer answerold, List<Car> carlist) {
        Answer answer = new Answer();
        List<List<Integer>> pathlist = new ArrayList<>();
        List<Integer> caridlist = new ArrayList<>();
        for (int i=0; i<carlist.size(); i++) {
            int j=0;
            for (j=0; j<answerold.getCarid().size(); j++) {
                if (carlist.get(i).getId() == answerold.getCarid().get(j)) {
                    break;
                }
            }
            caridlist.add(answerold.getCarid().get(j));
            pathlist.add(answerold.getPathList().get(j));
            System.out.println(answerold.getCarid().get(j));
            System.out.println(answerold.getPathList().get(j));
        }
        answer.setPathList(pathlist);
        answer.setCarid(caridlist);
        return answer;
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
                    lane2.add(new Lane());
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
