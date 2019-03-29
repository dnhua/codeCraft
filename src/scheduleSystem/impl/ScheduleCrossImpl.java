package scheduleSystem.impl;

import pojo.*;
import scheduleSystem.ScheduleCross;

import java.util.*;


public class ScheduleCrossImpl implements ScheduleCross {
    private Map<Integer, RoadInschedule> roads;
    private Map<Integer, CrossInschedule> crosses = new HashMap<>();
    public ScheduleCrossImpl(List<Cross> list, Map<Integer, RoadInschedule> roads) {
        this.roads = roads;
        for (Cross cross : list) {
            CrossInschedule crossInschedule = new CrossInschedule();
            crossInschedule.setId(cross.getId());   //通过cross来设置id
            //设置每个cross的roadIds(道路的id集合/按逆时针顺序)
            List<Integer> roadIds = getRoadIdList(cross);
            crossInschedule.setRoadIds(roadIds);
            //设置每个cross的道路roadIndexPQ
            List<Integer> roadIndexPQ = getRoadIndexPQ(cross);
            crossInschedule.setRoadIndexPQ(roadIndexPQ);
            crosses.put(crossInschedule.getId(), crossInschedule);
        }
        this.roads = roads;
    }

    public ScheduleCrossImpl(Map<Integer, CrossInschedule> crosses, Map<Integer, RoadInschedule> roads) {
        this.roads = roads;
        this.crosses = crosses;
    }


    public List getRoadIdList(Cross cross) {
        List idlist = new ArrayList();
        idlist.add(cross.getRoadId1());
        idlist.add(cross.getRoadId2());
        idlist.add(cross.getRoadId3());
        idlist.add(cross.getRoadId4());
        return idlist;
    }

    public List getRoadIndexPQ(Cross cross) {
        List<Integer> roadIndexPQ = new ArrayList();
        List<Integer> ids = getRoadIdList(cross);
        List<Integer> idstem = getRoadIdList(cross);
        Collections.sort(idstem);
        for (int i=0; i<idstem.size(); i++) {
            if (idstem.get(i) < 0)
                continue;
            int i1 = ids.indexOf(idstem.get(i));
            roadIndexPQ.add(i1);
        }
        return roadIndexPQ;
    }

    /**
     * 选一辆在等待车，无论是否与其他车发生冲突。
     * 如果前road没有满足条件的等待车辆则返回null。
     * @param road
     * @return
     */
    public CarInschedule getCarFromRoad(CrossInschedule cross, RoadInschedule road) {
        List<Lane> lanes = getLanes(cross, road);
        if (lanes == null)
            return null;

        for (Lane lane : lanes) {
            Deque<CarInschedule> cars = lane.getCars();
            for (CarInschedule car : cars) {
                if (car.isWaitflag())
                    return car;
            }
        }
        return null;
    }
    
    public List<Lane> getLanes(CrossInschedule cross, RoadInschedule road) {
        int beginId = road.getBeginId();
        int endId = road.getEndId();
        //debug:这里的判断错了
        String fromTo = cross.getId() != beginId ? beginId + "->" + endId : endId + "->" + beginId;

        Map<String, List<Lane>> lanemap = road.getLanemap();
        if(!lanemap.containsKey(fromTo)) {
            return null;
        }
        List<Lane>  lanes = lanemap.get(fromTo);
        return lanes;
    }

    public List<Lane> getLaneschiocelane(CrossInschedule cross, RoadInschedule road) {
        int beginId = road.getBeginId();
        int endId = road.getEndId();
        //debug:这里的判断错了
        String fromTo = cross.getId() == beginId ? beginId + "->" + endId : endId + "->" + beginId;

        Map<String, List<Lane>> lanemap = road.getLanemap();
        if(!lanemap.containsKey(fromTo)) {
            return null;
        }
        List<Lane>  lanes = lanemap.get(fromTo);
        return lanes;
    }

    @Override
    public void scheduleOneCross(CrossInschedule crossInschedule) {
        while (shceduleOneCrossOneRound(crossInschedule));
    }

    public boolean shceduleOneCrossOneRound(CrossInschedule crossInschedule) {
        System.out.println("3.1开始调度"+crossInschedule.getId()+"路口");
        List<Integer> roadsIndexPQ = crossInschedule.getRoadIndexPQ();
        List<Integer> roadIds = crossInschedule.getRoadIds();
        int[] flags = new int[4];
        //对于每条路(按优先级选取)
        for (int i=0; i<roadsIndexPQ.size(); i++) {
            if (i%4==0) {
                flags = new int[4];
            }
            Integer index = roadsIndexPQ.get(i);
            int roadid = roadIds.get(index);
            if (roadid == -1)   //如果此位置没有连接道路
                continue;
            System.out.println("3.2选取的路为:"+roadid);
            //选择一辆在排队的车
            RoadInschedule road = roads.get(roadid);
            CarInschedule car = getCarFromRoad(crossInschedule, road);

            //调度这条路，直到car为空，或者冲突了
            while (true) {
                road = roads.get(roadid);
                car = getCarFromRoad(crossInschedule, road);
                if (car==null || isConflicted(crossInschedule, car, i))
                    break;
                System.out.println("选取的车为:"+car.getId());

                //1.判断该车是否可以通过下一个车道
                if (!car.isCanOutCross()) {
                    car.setLocation(car.getLocation() + car.getRealspeed());
                    if (car.getLocation()+car.getRealspeed()>car.getDistance())
                        car.setCanOutCross(true);
                    road.updateFirst(car);
                    //更新map roads
                    roads.put(road.getId(), road);
                    flags[index] = 1;   //index这条路动了
                    System.out.println("该车无法通过");
                    System.out.println("car的当前位置："+car.getLocation()+"   car的速度"+car.getRealspeed()
                                        +"  car的限速"+car.getSpeedlimit()+"  road的限速 "+car.getRoadspeedlimit());
                    continue;
                }
                System.out.println("该车可以通过");
                //2.判断此车是否到达目的地
                List<List<Integer>> pathList = ScheduleImpl.answer.getPathList();
                List<Integer> carid = ScheduleImpl.answer.getCarid();
                int caridindex = carid.indexOf(car.getId());
                List<Integer> path = pathList.get(caridindex);
                if (car.getRoadid() == car.getNextroadid()) {
                    car.setDone(true);
                    road.removeFirst(car);   //删除旧road里面的car
                    caridindex = carid.indexOf(car.getId());
                    ScheduleImpl.answer.getCarid().remove(caridindex);
                    ScheduleImpl.answer.getPathList().remove(caridindex);
                    continue;
                }

                //3.选择车道,如果找不到合适的车道，则跳出这条路的调度
                int nextroadid = car.getNextroadid();
                RoadInschedule nextroad = roads.get(nextroadid);
                int ilane = choiceLane(crossInschedule, nextroad);
                if (ilane == -1)
                    break;
                flags[index] = 1;   //index这条路动了

                //4.计算在下一个车道可以行进的距离
                int s2 = car.getDistance() - car.getLocation();

                //5.更新car，更新road，旧road里面的car要删除，新road添加
                int oldlaneid = car.getLaneid();    //保存旧的lane的id
                nextroad = roads.get(nextroadid);
                road.removeFirst(car);   //删除旧road里面的car

                RoadInschedule roadold = road;
                car.setRoadid(nextroadid);  //更新roadid
                car.setRoadspeedlimit(nextroad.getSpeedLimit());
                car.setRealspeed(Math.min(car.getRoadspeedlimit(), car.getRoadspeedlimit()));
                car.setLaneid(ilane);
                car.setWaitflag(false);
                car.setStopflag(true);

                Integer nid;
                if (path.get(path.indexOf(car.getRoadid())) == car.getDestination())
                    nid = path.get(path.indexOf(car.getRoadid()));
                else
                    nid = path.get(path.indexOf(car.getRoadid())+1);
                car.setNextroadid(nid);
                nextroad = roads.get(nid);
                RoadInschedule newroad = roads.get(car.getRoadid());  //使road重新指向下一条路
                car.setDistance(newroad.getLength());
                car.setCanOutCross(false);

                //计算下一个cross的id
                int nextcrossid = (crossInschedule.getId() == newroad.getBeginId() ?
                        newroad.getEndId() : newroad.getBeginId());
                car.setFromTo(crossInschedule.getId()+"->"+nextcrossid);
                int direction = getDirection(car.getRoadid(), car.getNextroadid(), crossInschedule);
                car.setDirection(direction);
                if (car.getLocation()+car.getRealspeed()>car.getDistance())
                    car.setCanOutCross(true);

                car.setNextroadid(nextroad.getId());
                int v2 = calcNextRoadMaxDistance(crossInschedule, car);
                s2 = v2 - s2 > 0 ? v2 - s2 : 0;
                if (s2 > getS2(s2, newroad, car.getFromTo(), ilane))
                    s2 = getS2(s2, newroad, car.getFromTo(), ilane);
                car.setLocation(s2);
                newroad.addLast(car);

                //6. 需要调度/更新一下该lane
                ScheduleRoadImpl scheduleRoad = new ScheduleRoadImpl(this.roads);
                int lastcorssid = (crossInschedule.getId() == roadold.getBeginId() ? roadold.getEndId():roadold.getBeginId());
                scheduleRoad.updateOne(roadold, oldlaneid, lastcorssid+"->"+crossInschedule.getId());

                //7.判断是否路已经满了，满了则可能死锁
                if (newroad.isfull(car.getFromTo())) {
                    System.out.println("deadlock: roadid: "+road.getId()+" begin id"+road.getBeginId()
                            +" end id"+road.getEndId());
                    try {
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //8. 最后需要更新roads，因为全程保存一份数据，所以这里不用更新
                roads.put(road.getId(), road);
                roads.put(newroad.getId(), newroad);
            }
            //9.对每一轮进行判断，如果只要有路动了，就认为这次调度是可以继续的
            if (i%4==3){
                return flags[0]==1 || flags[1]==1 || flags[2]==1 || flags[3]==1;
            }
        }
        return false;
    }

    public int getS2(int s, RoadInschedule road, String fromTo, int ilane) {
        Map<String, List<Lane>> lanemap = road.getLanemap();
        List<Lane> lanes = lanemap.get(fromTo);
        Lane lane = lanes.get(ilane);
        Deque<CarInschedule> cars = lane.getCars();
        if(cars!=null && cars.size()!=0) {
            CarInschedule last = cars.getLast();
            return s > last.getLocation() ? last.getLocation() : s;
        }
        return s;
    }

    public int getDirection (int roadid, int nextroadid, CrossInschedule cross) {
        int nextcrossid = roads.get(roadid).getBeginId() == cross.getId() ?
                            roads.get(roadid).getEndId() :
                            roads.get(roadid).getBeginId();
        CrossInschedule crossnnext = crosses.get(nextcrossid);
        List<Integer> roadIds = crossnnext.getRoadIds();
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

    public int calcNextRoadMaxDistance(CrossInschedule cross, CarInschedule car) {
        //通过car得到下一条路
        int nextroadid = car.getNextroadid();
        RoadInschedule nextroadInschedule = roads.get(nextroadid);
        int nextspeedLimit = nextroadInschedule.getSpeedLimit();
        int v2 = Math.min(car.getRealspeed(), nextspeedLimit);
        return v2;
    }

    @Override
    public int choiceLane(CrossInschedule cross, RoadInschedule road) {
        List<Lane> lanes = getLaneschiocelane(cross, road);
        if (lanes == null)
            return -1;
        int i = 0;
        for (; i<lanes.size(); i++) {
            Deque<CarInschedule> cars = lanes.get(i).getCars();
            if (cars == null || cars.size()==0)
                return i;
            CarInschedule last = cars.getLast();
            if (last.getLocation() > 0)
                return i;
        }
        return -1;
    }

    public boolean isConflicted(CrossInschedule cross, CarInschedule car, int i) {
        List<Integer> roadids = cross.getRoadIds();
        //直行
        if (car.getDirection() == 3)
            return false;
        CarInschedule car1;
        //左转
        if (car.getDirection() == 2){
            //直行冲突检查
            if (i == 0) {
                if (roadids.get(i+3) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(i+3)));
            } else {
                if (roadids.get(i-1) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(i-1)));
            }
            return  car1!=null && car1.getDirection()==3;
        } else {    //右转
            //直行冲突检查
            if (i == 3) {
                if (roadids.get(0) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(0)));
            } else {
                if (roadids.get(i+1) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(i+1)));
            }
            //左行冲突检查
            if (i == 0 || i == 1) {
                if (roadids.get(i+2) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(i+2)));
            } else if (i == 2) {
                if (roadids.get(0) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(0)));
            } else if (i==3) {
                if (roadids.get(1) == -1)
                    return false;
                car1 = getCarFromRoad(cross, roads.get(roadids.get(1)));
            }
            return (car1 != null && car1.getDirection() == 2) || (car1 != null && car1.getDirection() == 3);
        }
    }

    @Override
    public void scheduleAllCross() {
        //按优先级更新各个cross
        for (Map.Entry<Integer, CrossInschedule> entry : crosses.entrySet()) {
            scheduleOneCross(entry.getValue());
        }
    }
}
