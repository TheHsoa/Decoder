package com.nstu.substitutioncipher;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;

/**
 * Created by R_A_D on 08.11.2015.
 */

public class WorkWithFile {

    private String path;
    private long pointer;
    private RandomAccessFile file;

    public WorkWithFile(String path) {
        this.path = path;
        this.pointer = 0;
       if(!(new File(path).exists()))
        {
            File folder = new File(path.substring(0, path.lastIndexOf('\\')));
            folder.mkdirs();
        }
    }
    public long getPointer() {
        return pointer;
    }
    public void setPointer(long pointer) {
        this.pointer = pointer;
    }

    // этот метод читает файл и выводит его содержимое
    public String read() throws IOException {
        file = new RandomAccessFile(path, "r");
        String res = "";
        int b = file.read();
        // побитово читаем символы и плюсуем их в строку
        while(b != -1){
            if(b <= 255 && b >=192) b += 848;
            res = res + (char)b;
            b = file.read();
        }
        pointer = file.getFilePointer();
        file.close();
        return res;
    }

    public String readToNextSpace() throws IOException {
        file = new RandomAccessFile(path, "r");
        file.seek(pointer);
        String res = "";
        int b = file.read();
        // побитово читаем символы и плюсуем их в строку
        while(b != ' ' && b != -1){
            if(b <= 255 && b >=192) b += 848;
            res = res + (char)b;
            b = file.read();
        }
        pointer = file.getFilePointer();
        file.close();
        return res;
    }


    public String readLine() throws IOException {
        file = new RandomAccessFile(path, "r");
        file.seek(pointer);
       String res = "";
        char c = System.getProperty("line.separator").charAt(0);
        int b = file.read();
        // побитово читаем символы и плюсуем их в строку
        while((c != b) && (b != -1)){
            if(b <= 255 && b >=192) b += 848;
            res = res + (char)b;
            b = file.read();
        }
        pointer = file.getFilePointer()+1;
        file.close();
        return res;
    }

    // запись строки в конец файла
    public void writeLineInTheEnd(String st) throws IOException {

        file = new RandomAccessFile(path, "rw");
        pointer = file.length();
        file.seek(pointer);
        file.write((st + System.getProperty("line.separator")).getBytes("windows-1251"));
        file.close();
    }


}