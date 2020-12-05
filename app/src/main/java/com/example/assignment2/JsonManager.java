package com.example.assignment2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManager {
    ArrayList<Car> parceCarsData(String json){
        ArrayList<Car> carsFromAPI = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            JSONArray array =  new JSONArray(json);
            for (int i = 0 ; i< array.length(); i++){
                jsonObject = new JSONObject(array.getString(i));  //(array.getJSONObject(i));
                int carId = Integer.parseInt(jsonObject.getString("id"));
                String carmodel1 = jsonObject.getString("CarModel1");
                String carmodel2 = jsonObject.getString("CarModel2");
                Double carYear = Double.parseDouble(jsonObject.getString("Year"));

                /* You can also use getters and setters if not all objects have the same consistent data structure
                * BUT you should already have initialized values for variables of such in your class */
                Car car = new Car(carId,carmodel1, carmodel2, carYear, false);
                carsFromAPI.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carsFromAPI;
    }
}
