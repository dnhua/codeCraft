package utils;

import java.io.FileOutputStream;

import java.io.IOException;


public class WtriteData {

    public void fileOutputStream(String str, String filepath) throws IOException{
//        String temp="Hello world!\n";
        FileOutputStream fos = new FileOutputStream(filepath,false);//true表示在文件末尾追加
        fos.write(str.getBytes());
        fos.close();//流要及时关闭
    }
}
