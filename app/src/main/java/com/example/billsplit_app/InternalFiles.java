package com.example.billsplit_app;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class InternalFiles extends AppCompatActivity {
    private static String fileName;
    private static JSONObject data;

    public InternalFiles() throws JSONException {

        fileName = "datas.json";
        data = readDataFile();
    }
    public static JSONObject getDefault() throws JSONException {
        JSONObject tax = new JSONObject();
        tax.put("PST",0);
        tax.put("GST",0);
        tax.put("HST",0);

        JSONObject res = new JSONObject();
        res.put("people",0);
        res.put("cost",0);
        res.put("location","Choose Category");
        res.put("tax", tax);

        return res;
    }
    public static JSONObject getData(){
        return data;
    }


    public static String getSavedLocation() throws JSONException {
        return data.getString("location");
    }
    public static int getSavedPeople()throws JSONException {
        return data.getInt("people");
    }
    public static int getSavedCost()throws JSONException {
        return data.getInt("cost");
    }
    public static double getSavedTax() throws JSONException {
        JSONObject current = data.getJSONObject("tax");
        double pst = current.getInt("PST");
        double gst = current.getInt("GST");
        double hst = current.getInt("HST");

        return (pst + gst + hst)/100;

    }

    public static void setSomething(String item, int content) throws JSONException {
        data.remove(item);
        data.put(item, content);
    }
    public static void setSomething(String item, String content) throws JSONException {
        data.remove(item);
        data.put(item, content);
    }
    public static void setSomething(String item, JSONObject content) throws JSONException {
        data.remove(item);
        data.put(item, content);
    }


    @SuppressLint("SdCardPath")
    public static void saveData() {
        File path = new File("/data/user/0/com.example.billsplit_app/files");
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(data.toString().getBytes());
            writer.flush();
            writer.close();


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readDataFile() throws JSONException {

        String ret = "";

        try {
            File file = new File("/data/data/com.example.billsplit_app/files/datas.json");
            InputStream inputStream = new FileInputStream(file);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return getDefault();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return new JSONObject(ret.toString());
    }

}
