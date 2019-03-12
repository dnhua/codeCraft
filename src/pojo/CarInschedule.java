package pojo;

public class CarInschedule {
    private int id;
    private int roadid;
    private int location;
    private boolean waitflag = false;
    private boolean stopflag = false;
    private int speedlimit;
    private int realspeed;
    private int distance;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    private int roadspeedlimit;

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
}
