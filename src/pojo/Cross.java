package pojo;

public class Cross {
    private int id;
    private int roadId1;
    private int roadId2;
    private int roadId3;
    private int roadId4;
    static public final int numberPara = 5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoadId1() {
        return roadId1;
    }

    public void setRoadId1(int roadId1) {
        this.roadId1 = roadId1;
    }

    public int getRoadId2() {
        return roadId2;
    }

    public void setRoadId2(int roadId2) {
        this.roadId2 = roadId2;
    }

    public int getRoadId3() {
        return roadId3;
    }

    public void setRoadId3(int roadId3) {
        this.roadId3 = roadId3;
    }

    public int getRoadId4() {
        return roadId4;
    }

    public void setRoadId4(int roadId4) {
        this.roadId4 = roadId4;
    }

    @Override
    public String toString() {
        return "Cross{" +
                "id=" + id +
                ", roadId1=" + roadId1 +
                ", roadId2=" + roadId2 +
                ", roadId3=" + roadId3 +
                ", roadId4=" + roadId4 +
                '}';
    }
}
