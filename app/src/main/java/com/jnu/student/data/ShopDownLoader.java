package com.jnu.student.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

public class ShopDownLoader {

    public String download(String url_str)  {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(url_str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine())!=null){
                    stringBuilder.append(line);
                }
                String responseData = stringBuilder.toString();
                reader.close();
                connection.disconnect();
                return responseData;
            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<ShopLocation> parseJsonObjects(String content) {
        ArrayList<ShopLocation> locations=new ArrayList<>();
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            int length = jsonDatas.length();
            String test;
            for (int i = 0; i < length; i++) {
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                ShopLocation shopLocation = new ShopLocation();
                shopLocation.setName(shopJson.getString("name"));
                shopLocation.setLatitude(shopJson.getDouble("latitude"));
                shopLocation.setLongitude(shopJson.getDouble("longitude"));
                shopLocation.setMemo(shopJson.getString("memo"));
                locations.add(shopLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  locations;
    }
}