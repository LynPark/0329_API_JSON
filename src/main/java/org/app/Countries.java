package org.app;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Countries {

    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/TravelAlarmService2/getTravelAlarmList2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=pwYgSW17uyt9xaKGN%2FxDK7WRsDcgbEI9uiNCFvhfXYlCuUOZPwh1UD%2Bdh%2B9hzn%2F%2B3iL6beUXOaq%2FehouTQ0wUg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("198", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        JSONTokener tokener = new JSONTokener(sb.toString());
        JSONObject object = new JSONObject(tokener);
//        System.out.println(object);
        JSONArray arr = object.getJSONArray("data");
//        System.out.println(arr);


        for (Object one : arr) {
            JSONObject ob = (JSONObject) one;

            Object alarmLevelObject = ob.get("alarm_lvl");
            String alarmLevelString;
            Object rm = ob.get("remark");
            Object re_ob = "";

            if (alarmLevelObject != null && alarmLevelObject != JSONObject.NULL) {
                int alarmLevel = (int) alarmLevelObject;
                switch (alarmLevel) {
                    case 1:
                        alarmLevelString = "여행유의";
                        break;
                    case 2:
                        alarmLevelString = "여행자제";
                        break;
                    case 3:
                        alarmLevelString = "출국권고";
                        break;
                    case 4:
                        alarmLevelString = "여행금지";
                        break;
                    default:
                        alarmLevelString = "안전합니다.";
                        break;
                }
            } else {
                alarmLevelString = "안전합니다.";
                rm = re_ob;
            }


            System.out.print(ob.get("country_nm") + "\t");
            System.out.print("<" + alarmLevelString + ">\t");
            System.out.println(rm);
        }
    }
}
