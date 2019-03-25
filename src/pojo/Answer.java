package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结果
 */
public class Answer {
    List<Integer> carid = new ArrayList<>();
    // list[0] carid; list[1] 出发时间; list[2]...路径
    List<List<Integer>> pathList = new ArrayList<>();

    public List<Integer> getCarid() {
        return carid;
    }

    public void setCarid(List<Integer> carid) {
        this.carid = carid;
    }

    public List<List<Integer>> getPathList() {
        return pathList;
    }

    public void setPathList(List<List<Integer>> pathList) {
        this.pathList = pathList;
    }
}
