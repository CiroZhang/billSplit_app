package com.example.billsplit_app;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Internal_files extends AppCompatActivity {
    private static String fileName = "datas.json";

    public static void writeToDataFiles(String content) {
        @SuppressLint("SdCardPath") File path = new File("/data/user/0/com.example.billsplit_app/files");
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(content.getBytes());
            writer.flush();
            writer.close();


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JSONObject readDataFile(){

        try {
            System.out.println(openFileInput("datas.json"));
//            FileInputStream fis = openFileInput("datas.json");
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line + "\n");
//
//            }
//            return new JSONObject(String.valueOf(sb));
//
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;


    }
}
