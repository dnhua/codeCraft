package scheduleSystem;

import pojo.Cross;
import pojo.CrossInschedule;
import pojo.RoadInschedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
    public void scheduleOneCross() {

    }

    @Override
    public void scheduleAllCross() {

    }
}
