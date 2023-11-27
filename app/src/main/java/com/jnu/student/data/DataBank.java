package com.jnu.student.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    final String FILENAME = "bookitems.data";
    public String getFileName() {
        return FILENAME;
    }

    //读取保存的数据
    public ArrayList<Book> LoadBookItems(Context context) {
        ArrayList<Book> data = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (ArrayList<Book>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    //将数据序列化并保存到内部文件
    public void saveBookItems(Context context,ArrayList<Book> data) {
        try {
            FileOutputStream fos = context.openFileOutput(getFileName(), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
