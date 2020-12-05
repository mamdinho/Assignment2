package com.example.assignment2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.myViewHolder> {

    //interface below will return the car clicked
     interface AlertDialogListner {
        public void alertDialogSelectedCar(Car insertedCar);
        public void alertDialogRemovedCar(Car removedCar);
        public void carModelClicked(Car carClicked);
    }

    private Context context;
    private List<Car> carList;
    public AlertDialogListner listener;

    public CarAdapter(Context ctxt, List<Car> cars){
        this.context = ctxt;
        this.carList = cars;
        listener = (AlertDialogListner)context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =   LayoutInflater.from(context).inflate(R.layout.simple_view_item, parent, false);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Car currentCar = carList.get(position);
        holder.carmodel1.setText(currentCar.carModel1);
        holder.carmodel2.setText(currentCar.carModel2);
        String carname = currentCar.getCarModel1();
        if(carname.equalsIgnoreCase("Toyota")){
            holder.carImage.setImageResource(R.drawable.toyota);
        }else if(carname.equalsIgnoreCase("Subaru")){
            holder.carImage.setImageResource(R.drawable.subaru);
        }
        else if(carname.equalsIgnoreCase("Kia")){
            holder.carImage.setImageResource(R.drawable.kia);
        }
        else if(carname.equalsIgnoreCase("Mitsubishi")){
            holder.carImage.setImageResource(R.drawable.mitsubishi);
        }
        else if(carname.equalsIgnoreCase("Ford")){
            holder.carImage.setImageResource(R.drawable.ford);
        }
        else if(carname.equalsIgnoreCase("Nissan")){
            holder.carImage.setImageResource(R.drawable.nissan);
        }
        else if(carname.equalsIgnoreCase("Honda")){
            holder.carImage.setImageResource(R.drawable.honda);
        }else if(carname.equalsIgnoreCase("Mazda")){
            holder.carImage.setImageResource(R.drawable.mazda);
        }
        else if(carname.equalsIgnoreCase("Volkswagen")){
            holder.carImage.setImageResource(R.drawable.volkswagen);
        }
        //holder.carImage.setImageResource(R.drawable.carname);
        DatabaseClient.databaseWriteExecutor.execute(() -> {
            List<Car> carfromDb = DatabaseClient.getsDatabase().carDao().getCar(currentCar.getCarModel1());
            if(carfromDb.size() < 1){ //car doesn't exist
                /* Do nothing because car doesn't exist and unlike picture is default picture */
            }else{
                /* Run in main thread and modify image to set as favourite icon */
                currentCar.setIsFavourite(true);
                holder.imageView.setImageResource(R.drawable.like);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    /* Below inner class initializes text view and implements onClick Listener to add car to database */
     class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView carmodel1, carmodel2;
        ImageView imageView, carImage;
        View view;
        public myViewHolder(View itemView) {
            super(itemView);
            carmodel1 = (TextView)itemView.findViewById(R.id.carModel1);
            carmodel2 = (TextView)itemView.findViewById(R.id.carModel2);
            imageView = (ImageView)itemView.findViewById(R.id.listImage);
            carImage = (ImageView)itemView.findViewById(R.id.carImage);

            //itemView.setOnClickListener(this);
            view = itemView;
            carImage.setOnClickListener(this);
            carmodel1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
              /* Insert when clicked OK to database and changes icon */
            Car car = carList.get(getAdapterPosition());
            switch(v.getId()){
                case R.id.carImage:
                    DatabaseClient.databaseWriteExecutor.execute(() -> {
                        List<Car> carfromDb = DatabaseClient.getsDatabase().carDao().getCarId(car.getId());
                        if(carfromDb.size() < 1){ //car doesn't exist
                            car.setIsFavourite(false);
                        }else{
                            car.setIsFavourite(true);
                        }
                    });
                    if(!car.getFavourite()){
                        new AlertDialog.Builder(context)
                                .setTitle("Favorite Car?")
                                .setMessage("Are you sure you want to insert " + car.getCarModel1() + " to Database?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("car to save", car.getCarModel1());
                                        DatabaseClient.insertFavouriteCar(car);
                                        car.setIsFavourite(true);
                                        ImageView imageFav = (ImageView) view.findViewById(R.id.listImage);
                                        imageFav.setImageResource(R.drawable.like);
                                        listener.alertDialogSelectedCar(car);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else{ //Ask if user wants to delete car from db
                        new AlertDialog.Builder(context)
                                .setTitle("Remove Favorite Car?")
                                .setMessage("Are you sure you want to delete " + car.getCarModel1() + " from Database?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("car to delete", car.getCarModel1());
                                        DatabaseClient.deleteFavouriteCar(car);
                                        car.setIsFavourite(false);
                                        ImageView imageFav = (ImageView) view.findViewById(R.id.listImage);
                                        imageFav.setImageResource(R.drawable.unlike);
                                        listener.alertDialogRemovedCar(car);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    break;
                case R.id.carModel1:
                    listener.carModelClicked(car);
                    break;
            }

        }
    }
}
