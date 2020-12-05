package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/* Add implements to CarAdapter.AlertDialogListner */
public class MainActivity extends AppCompatActivity implements NetworkingClass.APIDataListener, CarAdapter.AlertDialogListner{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    NetworkingClass networkingClass;
    JsonManager jsonManager;
    CarAdapter adapter;
    ArrayList<Car> cars = new ArrayList<Car>();
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseClient.getInstance(this); //builds the database
        recyclerView = (RecyclerView) findViewById(R.id.carRecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        networkingClass = new NetworkingClass(this,getApplicationContext());
        jsonManager = new JsonManager();

        networkingClass.getAllCars();

    }

    @Override
    public void returnAPIData(String data){
        Log.d("data",data);
        ArrayList<Car> result = jsonManager.parceCarsData(data);
        cars = new ArrayList<Car>();
        cars.addAll(result);
        recyclerView.setAdapter(new CarAdapter(this,cars));
        recyclerView.invalidate();
    }


    @Override
    public void alertDialogSelectedCar(Car insertedCar){
        Toast.makeText(this,insertedCar.getCarModel1() +" is Saved to DB",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void alertDialogRemovedCar(Car deletdCar){
        Toast.makeText(this,deletdCar.getCarModel1() +" is Deleted from DB",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void carModelClicked(Car carClicked) {
        Toast.makeText(this,carClicked.getCarModel1(),Toast.LENGTH_SHORT).show();
    }
}