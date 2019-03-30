package utils;

import pojo.Answer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    static public FileReader getFileReader(String path) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileReader;
    }
    static  public void closeFileReader(FileReader fileReader) {
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public BufferedReader getBufferedReader(FileReader fileReader) {
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(fileReader);
        return bufferedReader;
    }
    static  public void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ListAnswer2txt(Answer answer, String savepath) {
        StringBuffer stringBuffer = new StringBuffer();
        List<List<Integer>> pathList = answer.getPathList();
        List<Integer> carid = answer.getCarid();
        for (int i = 0; i< pathList.size(); i++){
            List<Integer> path = pathList.get(i);
            stringBuffer.append("("+carid.get(i)+",");
            for(int j = 1; j < path.size(); j++) {
                //如果不是最后一个元素，就加，
                if (j==path.size()-1)
                    stringBuffer.append(path.get(j));
                else
                    stringBuffer.append(path.get(j)+",");
            }
            stringBuffer.append(")\n");
        }
        String str = stringBuffer.toString();
        WtriteData wd = new WtriteData();
        try {
            wd.fileOutputStream(str, savepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
