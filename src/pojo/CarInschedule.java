package pojo;

import java.util.Objects;

public class CarInschedule {
    private int id;
    private int roadid;
    private int nextroadid;
    private int nextcrossid;
    private int laneid;
    private int location;
    private boolean waitflag = false;
    private boolean stopflag = false;
    private int speedlimit;
    private int realspeed;
    private int distance;
    private boolean canOutCross = false;
    private int roadspeedlimit;
    private String fromTo;
    private boolean done = false;   //是否到达目的地
    private int direction = 3;  //3直行，2是左转，1是右转。默认直行，在每次变为等待状态时应该更新方向。
    private int destination;
    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public int getLaneid() {
        return laneid;
    }

    public void setLaneid(int laneid) {
        this.laneid = laneid;
    }

    public boolean isCanOutCross() {
        return canOutCross;
    }

    public void setCanOutCross(boolean canOutCross) {
        this.canOutCross = canOutCross;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoadid() {
        return roadid;
    }

    public void setRoadid(int roadid) {
        this.roadid = roadid;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public boolean isWaitflag() {
        return waitflag;
    }

    public void setWaitflag(boolean waitflag) {
        this.waitflag = waitflag;
    }

    public boolean isStopflag() {
        return stopflag;
    }

    public void setStopflag(boolean stopflag) {
        this.stopflag = stopflag;
    }

    public int getSpeedlimit() {
        return speedlimit;
    }

    public void setSpeedlimit(int speedlimit) {
        this.speedlimit = speedlimit;
    }

    public int getRealspeed() {
        return realspeed;
    }

    public void setRealspeed(int realspeed) {
        this.realspeed = realspeed;
    }

    public int getRoadspeedlimit() {
        return roadspeedlimit;
    }

    public void setRoadspeedlimit(int roadspeedlimit) {
        this.roadspeedlimit = roadspeedlimit;
    }

    public int getNextroadid() {
        return nextroadid;
    }

    public void setNextroadid(int nextroadid) {
        this.nextroadid = nextroadid;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getNextcrossid() {
        return nextcrossid;
    }

    public void setNextcrossid(int nextcrossid) {
        this.nextcrossid = nextcrossid;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarInschedule that = (CarInschedule) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CarInschedule{" +
                "id=" + id +
                '}';
    }
}
