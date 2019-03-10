package test;

import pojo.Road;
import utils.ReadData;

import java.io.IOException;
import java.util.List;

public class TestUtils {
    List<Road> list;

    @org.junit.jupiter.api.Test
    public void testReadRoads() {
        List<Road> list;
        try {
            list = ReadData.readRoads("data/roads.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
