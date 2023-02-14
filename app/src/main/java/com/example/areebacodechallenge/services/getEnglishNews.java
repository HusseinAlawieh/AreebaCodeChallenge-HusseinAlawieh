package com.example.areebacodechallenge.services;


import android.os.AsyncTask;
import android.os.StrictMode;

import com.example.areebacodechallenge.article;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class getEnglishNews  {
    public  ArrayList<article>articles=new ArrayList<>();
    public void setConn(){

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL("https://content.guardianapis.com/search?api-key=488d778b-7c40-455e-b74a-3bc8b16d9f0a");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();


                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);
                JSONObject response = (JSONObject) data_obj.get("response");
                JSONArray arr = (JSONArray) response.get("results");
                for (int i = 0; i < arr.size(); i++) {
                    article a = new article();
                    JSONObject new_obj = (JSONObject) arr.get(i);
                    a.setId((String) new_obj.get("id"));
                    a.setWebUrl((String) new_obj.get("webUrl"));
                    a.setSectionName((String) new_obj.get("sectionName"));
                    a.setWebTitle((String) new_obj.get("webTitle"));
                    a.setWebPublicationDate((String) new_obj.get("webPublicationDate"));
                    articles.add(a);
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
