package utils;
import pojo.Road;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadData {
    static public List<Road> readRoads(String path) throws IOException {
        List<Road> list = new LinkedList<>();
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line=bufferedReader.readLine())!=null) {
            System.out.println(line);
        }
        return list;
    }
}
