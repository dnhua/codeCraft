package scheduleSystem;

import pojo.Cross;
import pojo.CrossInschedule;
import pojo.RoadInschedule;

import java.util.List;
import java.util.PriorityQueue;

public class ScheduleCrossImpl implements ScheduleCross {
    PriorityQueue<CrossInschedule> crosses = null;

    public ScheduleCrossImpl(List<Cross> list) {
        for (Cross cross : list) {  //对于每个cross来说，都要生成一个对应的
                                    //CrossInschedule放到PriorityQueue croses中


        }
    }

    @Override
    public void scheduleOneCross() {

    }

    @Override
    public void scheduleAllCross() {

    }
}
