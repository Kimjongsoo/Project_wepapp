package com.example.soo.webapp;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetDataSearch extends GetRequest {
    public GetDataSearch(Activity activity) {
        super(activity);
    }

    @Override
    protected void onPreExecute() {
        //EditText server =  activity.findViewById(R.id.server);
        String serverURLStr = "http://13.125.246.86:3000/employees";
        try {
            url = new URL(serverURLStr+"/get"+"-"+"data");  // 여기서 AWS 주소를 넣어야 한다.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        ArrayList<Contents> arrayList = getArrayListFromJSONString(jsonString);

        MyAdapter adapter =new MyAdapter(activity, R.layout.adapter,arrayList);
        ListView txtList = activity.findViewById(R.id.listview);
        txtList.setAdapter(adapter);
        txtList.setDividerHeight(10);
    }



    protected ArrayList<Contents> getArrayListFromJSONString(String jsonString) {
        EditText search=(EditText)activity.findViewById(R.id.search);

        ArrayList<Contents> output = new ArrayList();
        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                if ((jsonObject.getString("title")).contains(search.getText().toString())) {


                    Contents book = new Contents(jsonObject.getString("_id"),
                            jsonObject.getString("category"),
                            jsonObject.getString("title"),
                            jsonObject.getString("content"),
                            jsonObject.getString("price"),
                            jsonObject.getString("imgurl"));

                    output.add(book);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
