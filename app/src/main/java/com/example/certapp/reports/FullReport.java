package com.example.certapp.reports;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.certapp.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FullReport extends AppCompatActivity {

    String str;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);

        Intent intent = getIntent();
         str= intent.getStringExtra("reportDocument");
        Log.e("report values ",str);

//
//        try {
////            object = new JSONObject(str);
////            JSONObject employee = object.getJSONObject("map_value");
//
//            title = json.getString("title");
//            Log.e("Title of report ",title);
//            Log.e("Empli",employee.getString("fields"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
////        Log.e("Object values is ",object.toString());
//
        try {
            JSONArray jsonArray = new JSONArray(str);
            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);

                    if(jsonObject == null) {
                        continue;
                    }

                    String name = jsonObject.optString("title");
                    Log.e("Title pf tje report",name);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView reportTitle = findViewById(R.id.tvTitle);


        reportTitle.setText(title);


    }
//    private static JSONObject createJSONObject(String jsonString){
//        JSONObject  jsonObject=new JSONObject();
//        JsonParser jsonParser=new  JsonParser();
//        if ((jsonString != null) && !(jsonString.isEmpty())) {
//            try {
//                jsonObject=(JSONObject) jsonParser.parse(jsonString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return jsonObject;
//    }
}